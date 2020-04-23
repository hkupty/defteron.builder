# defteron.builder

Protobuf compilation for tools.deps

## Usage

Add `defteron.builder` in your `deps.edn` file:

```clojure
;; In your deps.edn
;; :aliases {
:protoc {:extra-paths ["proto"]
         :extra-deps {defteron.builder {:git/url "https://github.com/hkupty/defteron.builder"
                                        :sha "6e6961f6e005efc1d39d385a56969339dacce419"}}
         :main-opts ["-m" "defteron.builder"
                     "-p" "proto" ;; Same as :extra-paths, where your .proto files are stored
                     "-c" "classes" ;; Where compiled java representation will be put
                     ]}
;;}
```

And then call it from the command line: `clojure -Aprotoc`
