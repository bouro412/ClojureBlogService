(defproject blog "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [clj-time "0.14.0"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [environ "1.0.1"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [org.postgresql/postgresql "9.4-1205-jdbc42"]
                 [bouncer "0.3.3"]
                 [ring/ring-defaults "0.1.5"]]
  :plugins [[lein-environ "1.0.1"]]
  :uberjar-name "blog.jar"
  :min-lein-version "2.5.3"
  :main blog.core
  :profiles
  {:dev {:dependencies [[prone "0.8.2"]]
         :env {:dev true
               :db {:dbtype "postgresql"
                    :dbname "blog_dev"
                    :host "localhost"
                    :port 5432
                    :user "keita"
                    :password "hoge"}}}
   :uberjar {:aot :all
             :main blog.main}})
