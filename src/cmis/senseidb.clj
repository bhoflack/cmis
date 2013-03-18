(ns cmis.senseidb
  (:import [com.senseidb.search.client.json.req SenseiClientRequest$Builder]))

(defprotocol Predicate
  (sensei-or      [this exprs])
  (sensei-and     [this exprs])
  (sensei-not     [this exprs])
  (sensei-between [this exprs])
  (sensei-=       [this exprs])

(defrecord APredicate [stmt env cols]
  Object
  (toString [this] (apply str (flatten stmt)))
  Predicate
  (sensei-or      [this exprs])
  (sensei-and     [this exprs])
  (sensei-not     [this exprs])
  (sensei-between [this exprs]

    ))
    
(defprotocol Query
  (select* [this clause]))
  

(defrecord AQuery [builder]
  Object
  Query
  (select* [this clause]
    (println "select*")))

(defn query []
  (AQuery. (SenseiClientRequest$Builder.)))
  

  
  
  

