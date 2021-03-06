(defproject clojure-getting-started "1.0.0-SNAPSHOT"
  :description "Clojure web app experimentation"
  :url "https://still-falls-1437.herokuapp.com/"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "0.5.0"]
                 [camel-snake-kebab "0.2.4"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [postgresql "9.1-901-1.jdbc4"]]             
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "clojure-getting-started-standalone.jar"
  :profiles {:production {:env {:production true}}})
