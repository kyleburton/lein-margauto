# lein-margauto

Leiningen Plugin for [Marginalia](https://github.com/fogus/marginalia)
that watches your source directories for changes to your clojure
source files and rebuilds the Marginalia documentation whenever you
update your source code.

## Usage

    lein margauto

## Configuration

    (defproject my-project "1.0.0"
      ...
      :margauto {
        :src-dirs      ["src" "test"]
        :sleep-time 1000
        :port            3000})

### `:src-dirs`

By default `lein-margauto` only searches `src/` for files that end in
`.clj`.  You can change this behavior by setting `:src-dirs` to a
vector of directories to search.

### `:sleep-time`

`lein-margauto` uses a brute force appraoch to detecting changes in
your sources.  It performs a recursive search through the `:src-dirs`
rebuilding when it sees a change.  `:sleep-time` controls how long
`lein-margauto` will pause before checking the directory structure
again.

The default is 100 ms (1 second).

### `:port`

The port on which to run the documenation server.  The default is 3000.

## License

Copyright (C) 2011 Kyle R. Burton <kyle.burton@gmail.com>

Distributed under the Eclipse Public License, the same as Clojure.
