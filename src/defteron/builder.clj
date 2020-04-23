(ns defteron.builder
  (:require [badigeon.javac :refer [javac]]
            [badigeon.exec :refer [exec]]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io])
  (:import (java.nio.file Files)
           (java.nio.file.attribute FileAttribute))
  (:gen-class))

(def cli-options
  [["-p" "--proto DIR" "Directory where protobuf files are stored"
    :default "proto"]
   ["-c" "--compiled DIR" "Directory where compiled java classes will be put"
    :default "compiled"]
   ["-h" "--help"]])

(def ^:dynamic proto-dir nil)
(def ^:dynamic classes-dir nil)

(defn new-temp-dir [] (Files/createTempDirectory "java_protobuf" (into-array FileAttribute [])))

(defn proto-files []
  (filter
    #(and (-> % (.getAbsolutePath) (clojure.string/ends-with? ".proto"))
          (.isFile %))
    (file-seq proto-dir)))

(defn -main [& args]
  (let [{:keys [options summary]} (parse-opts cli-options args)
        temp-dir (new-temp-dir)]
    (if (:help options)
      (println summary)
      (binding [proto-dir (io/file (:proto options))
                classes-dir (io/file (:compiled options))]

        (println ::compiling.proto)
        (run! #(exec "protoc" {:proc-args ["-I" (.getAbsolutePath proto-dir)
                                           "--java_out" (.toString temp-dir)
                                           (.getAbsolutePath %)]})
              (proto-files))
        (println ::compiling.java)
        (javac (.toString temp-dir)
               {:compile-path (.getAbsolutePath classes-dir)})))
    (Files/delete temp-dir)))
