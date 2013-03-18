(ns cmis.blaat)

(defprotocol Predicate
  (predicate-and* [this exprs]))

(defrecord APredicate [statement]
  Object
  Predicate
  (predicate-and* [this exprs]
    (assoc this
      :statement (mapcat :statement exprs))))

(defn =*
  [builder f s]
  `(.addSelection ~builder (Selection/terms ~f ~s)))

(defn between*
  [builder col lower higher]
  `(.addSelection ~builder (Selection/range ~col ~lower ~higher)))

(defn sort*
  [builder col]
  `(.addSort Sort/desc col))