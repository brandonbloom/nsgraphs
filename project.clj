(defproject nsgraphs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.namespace "0.2.7"]
                 [me.raynes/fs "1.4.4"]
                 [rhizome "0.2.1"]
                 [aysylu/loom "0.5.0"]
                 ]

  :jvm-opts ["-server" "-Xss1m" "-Xmx4g"]

  )
