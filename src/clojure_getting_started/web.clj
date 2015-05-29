(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [clojure.java.jdbc :as db]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [camel-snake-kebab.core :as kebab]
            [clojure-getting-started.normalize :refer [normalize]]))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (concat (for [kind ["camel" "snake" "kebab"]]
                   (format "<a href=\"/%s?input=%s\">%s %s</a><br />"
                           kind "sample" kind "sample"))
                 ["<hr /><ul>"]
                 (for [s (db/query (env :database-url)
                                   ["select content from sayings"])]
                   (format "<li>%s</li>" (:content s)))
                 ["</ul><br /><br /><br />Normal:"]
                 	(normalize {"abe" [10 5 2 1 -2 -4] "ben" [9 6 6 0 -5 -4] "carl" [32 -2 -3 -4 -5 -6]}))})


(defn record [input]
  (db/insert! (env :database-url "postgres://localhost:5432/kebabs")
              :sayings {:content input}))

(defroutes app
  (GET "/camel" {{input :input} :params}
       (record input)
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (kebab/->CamelCase input)})
  (GET "/snake" {{input :input} :params}
       (record input)
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (kebab/->snake_case input)})
  (GET "/kebab" {{input :input} :params}
       (record input)
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (kebab/->kebab-case input)})
  (GET "/" []
       (splash))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

