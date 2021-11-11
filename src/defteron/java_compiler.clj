(ns defteron.builder
  (:require [badigeon.javac :refer [javac]]
            [badigeon.exec :refer [exec]]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io])
  (:import (java.nio.file Files)
           (java.nio.file.attribute FileAttribute))
  (:gen-class))

(def cli-options
  [["-j" "--java DIR" "Java directory"]
   ["-c" "--compiled DIR" "Directory where compiled java classes will be put"
    :default "classes"]
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
  (let [{:keys [options summary]} (parse-opts args cli-options)
        temp-dir (new-temp-dir)]
    (if (:help options)
      (println summary)
      (binding [classes-dir (io/file (:compiled options))]

        (println ::compiling.java)
        (javac (.toString temp-dir)
               {:compile-path (.getAbsolutePath classes-dir)})))))
