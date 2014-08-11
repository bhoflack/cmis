(ns cmis.service.product-test
  (:use clojure.test
        cmis.service.product)
  (:require [cmis.db :as db]))

(deftest insert-test
    (let [ds {:subprotocol "hsqldb"
              :subname "mem:cmis"
              :user "sa"
              :password ""}
          ps (cmis.service.product.ProductService. ds)]
      (db/with-database! ds
        (testing "Inserting to the product service"
          (-> {:background "Some document types need unique numbers (shipping documents,request for quote, ...).For managing these in a not-collocated environment a central document number request system has been setup."
               :ops_support "MCL"
               :installed_instances ["docnumserver.tess.elex.be"]
               :name "Document Number Server"
               :nagios_classes ""
               :users "???"
               :critical_level 0
               :version "1.0"
               :development "???"
               :functionality
  "- Give out unique numbers for documents\n- keep some basic information regarding that document."
               :cfengine_classes ""
               :business_owner "???"}
              (->> (.put ps))
              (type)
              (= java.util.UUID)))

        (testing "The link between the hostname and the product"
          (let [productid (-> {:background "Confluence is an enterprise Wiki which helps team collaboration and knowledge sharing."
                               :ops_support "SDI"
                               :installed_instances ["confluence-xpeqt-test.colo.elex.be"
                                                     "confluence-xpeqt-uat.colo.elex.be"
                                                     "confluence-xpeqt.colo.elex.be"]
                               :name "Confluence"
                               :nagios_classes "confluence"
                               :users "???"
                               :critical_level 0
                               :version "3.5.13"
                               :development "SDI"
                               :functionality "- share info\n- update info\n- collaborate"
                               :cfengine_classes "colo, confluence_servers_java6"
                               :business_owner "SDI"}
                              (->> (.put ps)))]

            (doall (for [hostname ["confluence-xpeqt-test.colo.elex.be"
                                   "confluence-xpeqt-uat.colo.elex.be"
                                   "confluence-xpeqt.colo.elex.be"]]
                   
                       (is (some #(= productid %)
                                 (.products-for-hostname ps hostname)))))
            
            )))))

