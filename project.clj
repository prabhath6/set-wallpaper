(defproject set-wallpaper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [enlive "1.1.6"]
                 [http-kit "2.3.0"]]
  :main ^:skip-aot set-wallpaper.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
