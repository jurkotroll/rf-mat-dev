(ns rf-mat-dev.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [rf-mat-dev.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
