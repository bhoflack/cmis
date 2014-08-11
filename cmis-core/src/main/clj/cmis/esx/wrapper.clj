(ns cmis.esx.wrapper
  (:import (com.vmware.vim25 ManagedObjectReference ObjectSpec))
  )

(defn object-spec
  [^ManagedObjectReference obj & {:keys [selectSet ^Boolean skip]}]
  (let [os (ObjectSpec.)]
    (.setObj os obj)
    (if (not (nil? selectSet))
      (.setSelectSet os selectSet))
    (if (not (nil? skip))
      (.setSkip os skip))    
    os))

