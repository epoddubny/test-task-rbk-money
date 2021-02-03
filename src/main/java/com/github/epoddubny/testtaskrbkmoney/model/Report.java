package com.github.epoddubny.testtaskrbkmoney.model;

import org.apache.avro.specific.SpecificRecord;

public interface Report {
    SpecificRecord getAvroReport();
}
