(ns leiningen.isolate
  (:require [leiningen.core
             [project :as project]
             [main :as main]]
            [leiningen.source-deps :refer [source-deps]]
            [mranderson.plugin :as mranderson]
            [clojure.java.io :as io]))

;; ## Data

(def ^:private +profiles+
  {::mranderson
   (-> (io/resource "mranderson/profiles.clj")
       (slurp)
       (read-string)
       (:config))})

;; ## Aliases

(def ^:private +aliases+
  {"deploy"  ["isolate" "deploy"]
   "install" ["isolate" "install"]
   "jar"     ["isolate" "jar"]
   "uberjar" ["isolate" "uberjar"]})

(defn- inject-aliases
  [project]
  (let [existing-aliases (set (keys (:aliases project)))]
    (-> project
        (update :aliases #(merge +aliases+ %))
        (assoc ::existing-aliases existing-aliases))))

(defn- reset-aliases
  [project]
  (update project :aliases select-keys (::existing-aliases project)))

;; ## Logic

(defn- isolate!
  [{:keys [name]
    :or {name "__isolated__"}
    :as project}]
  (main/resolve-and-apply project ["clean"])
  (let [prefix (.replace ^String name "-" "_")]
    (source-deps project
                 ":skip-javaclass-repackage" "true"
                 ":project-prefix" prefix)))

(defn- run-task!
  [project args]
  (when (seq args)
    (-> project
        (project/merge-profiles [::mranderson])
        (reset-aliases)
        (main/resolve-and-apply args))))

;; ## Middleware

(defn middleware
  [project]
  (let [existing-aliases (set (keys (:aliases project)))]
    (-> project
        (project/add-profiles +profiles+)
        (inject-aliases)
        (assoc ::middleware-active? true)
        (mranderson/middleware))))

(defn- check-middleware!
  [project]
  (when-not (::middleware-active? project)
    (main/info "Cannot run 'lein isolate' since middleware is not active.")
    (main/exit 1)))

;; ## Main

(defn isolate
  "Run a task in an isolated fashion, inlining all dependencies marked with
   `^:source-dep` metadata.

   Make sure to enable the respective middleware via:

       :middlewares [leiningen.isolate/middleware]

   See also:
   https://github.com/benedekfazekas/mranderson
   "
  [project & args]
  (doto project
    (check-middleware!)
    (isolate!)
    (run-task! args)))
