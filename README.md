# lein-isolate [![Clojars Project](https://img.shields.io/clojars/v/lein-isolate.svg)](https://clojars.org/lein-isolate)

__lein-isolate__ is a helper to perform automatic dependency isolation when
creating a Leiningen plugin. This uses [mranderson][mranderson] internally
to inline Clojure files.

[mranderson]: https://github.com/benedekfazekas/mranderson

## Installation

After including the plugin activate its middleware:

```clojure
:middlewares [leiningen.isolate/middleware]
```

## Usage

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
