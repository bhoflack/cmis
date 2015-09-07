(ns cmis.jira
  (:require clj-http.client
            clojure.data.json))

(defn view-issue
  "View the issue with the given key"
  [key jira-url username password]
  (-> jira-url
      (str "/rest/api/2/issue/" key)
      (clj-http.client/get {:basic-auth [username password] :accept :json})
      (:body) 
      (clojure.data.json/read-str) 
      (get "fields")
      (->> (filter (fn [[k v]] (not (.startsWith k "custom")))))
      (->> (into {}))))