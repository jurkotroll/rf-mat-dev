(ns rf-mat-dev.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [rf-mat-dev.core-test]))

(doo-tests 'rf-mat-dev.core-test)
