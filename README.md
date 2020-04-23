# defteron.builder

Protobuf compilation for tools.deps

## Usage

Add `defteron.builder` in your `deps.edn` file:

```clojure
;; In your deps.edn
;; :aliases {
:protoc {:extra-paths ["proto"]
         :extra-deps {defteron.builder {:git/url "https://github.com/hkupty/defteron.builder"
                                        :sha "449f891de1031c6cd3066d733fd152b3e404633a"}}
         :main-opts ["-m" "defteron.builder"
                     "-p" "proto" ;; Same as :extra-paths, where your .proto files are stored
                     "-c" "classes" ;; Where compiled java representation will be put
                     ]}
;;}
```

And then call it from the command line: `clojure -Aprotoc`
