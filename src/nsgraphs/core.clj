(ns nsgraphs.core
  (:require [clojure.tools.namespace.file :as ns-file]
            [clojure.tools.namespace.parse :as ns-parse]
            [clojure.set :as set]
            [me.raynes.fs :as fs]
            [loom.graph :as graph]
            [loom.alg :as alg]))

(def pattern #".*\.clj$")

(defn graph [& roots]
  (->> (for [root roots
             file (fs/find-files root pattern)
             :let [decl (ns-file/read-file-ns-decl file)
                   ns (second decl)]
             :when (symbol? ns)
             :let [deps (ns-parse/deps-from-ns-decl decl)]]
         [ns deps])
       (into {})))

(def adj (graph "/Users/brandon/Projects/circle/circle/src"
                "/Users/brandon/Projects/circle/circle/test"))

(def g (graph/digraph adj))

(defn web-ns? [sym]
  (.startsWith (str sym) "circle.web."))

(def web-reachable
  (set (for [ns (keys adj)
             :when (web-ns? ns)
             node (alg/bf-traverse g ns)]
         node)))

(def outside-web
  (set (for [start (graph/nodes g)
             :when (not (web-ns? start))
             node (next (alg/bf-traverse g start
                          :when (fn [neighbor predecessor depth]
                                  (not (web-ns? neighbor)))))]
         node)))

(def web-only (set/difference web-reachable outside-web))

(def belongs-in-web (remove web-ns? web-only))

(comment

  ;; Run me!
  (fipp.edn/pprint belongs-in-web)

)


(comment

(require '[rhizome.dot :as dot]
         '[rhizome.viz :as viz])

(defn view [graph]
  (spit "graph.dot" (dot/graph->dot
  ;(viz/view-graph
       (keys graph)
       graph
       :node->descriptor (fn [x] {:label x})
       ;:options {:dpi 72}
       ))
  )

  (view adj)

)
