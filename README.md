# lein-isolate [![Clojars Project](https://img.shields.io/clojars/v/lein-isolate.svg)](https://clojars.org/lein-isolate)

__lein-isolate__ is a helper to perform automatic dependency isolation when
creating a Leiningen plugin.

Internally, [mranderson][mranderson] is used to inline Clojure files and
redirect `require` calls to the appropriate namespaces.

[mranderson]: https://github.com/benedekfazekas/mranderson

## Installation

After including the plugin activate its middleware:

```clojure
:middleware [leiningen.isolate/middleware]
```

You can add an `:isolate` key to your project to customize arguments passed to
mranderson:

```clojure
:middleware [leiningen.isolate/middleware]
:isolate {:args [":skip-javaclass-repackage" "true"]}
```

## Usage

Mark the dependencies to-be-inlined with `^:source-dep` metadata, as required by
[mranderson][mranderson]. E.g.:,

```clojure
:dependencies [^:source-dep [rewrite-clj "0.6.1"]
               ^:source-dep [cheshire "5.8.0"]]
```

The middleware overrides the `install`, `deploy`, `jar` and `uberjar` tasks to
perform dependency isolation before they are run. So, usually, you won't have to
do anything more than activating it as per the instructions above.

To call the plugin directly, e.g. to verify that tests are still passing after
dependencies are isolated, use:

```sh
lein isolate <task> <arguments ...>
```

## License

```
MIT License

Copyright (c) 2018 Yannick Scherer

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
