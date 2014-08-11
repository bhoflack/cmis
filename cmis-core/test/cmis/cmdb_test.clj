(ns cmis.cmdb-test
  (:require [clojure.data :refer [diff]])
  (:use clojure.test
        cmis.cmdb))

(deftest parse-product-page-test
  (testing "Parse the product information"
    (let [fis (java.io.FileInputStream. "test-resources/itbi_product_page.html")
          expected {:name "ITBI"
                    :background "Feedback is the breakfast of champions is often told. In order to provide timely relevant feedback to everyone the data collection and presentation of the performance metrics must be automated so everyone can see at all times the current performance levels."
                    :functionality "- Collect and aggregate data for better reporting and information presentation
- Allow ad-hoc and predefined reporting
- Distribute information realtime or scheduled"
                    :critical_level 0
                    :business_owner "VLO"
                    :development "PTI"
                    :ops_support "NKI"
                    :users "???"
                    :version "3.6.0+nmu2"
                    :cfengine_classes ""
                    :nagios_classes "^itbi"
                    :installed_instances ["itbi-test.colo.elex.be"
                                          "itbi-uat.colo.elex.be"
                                          "itbi.colo.elex.be"]
                    }]

      (is (= (diff expected (parse-product-page fis))
             (seq [nil nil expected]))))
    ))

(deftest parse-product-list-page-test
  (testing "Parse a product list page"
    (is (= 100 (-> "test-resources/products_page_1.html"
                   (java.io.FileInputStream.)
                   (parse-product-list-page)
                   (:products)
                   (count)))))

  (testing "List all products in all pages"
    (is (= 332
           (-> "test-resources/products_page_1.html"
               (java.io.FileInputStream.)
               (list-products)
               (count))))))

