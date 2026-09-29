package com.dev.ssc;

import com.dev.ssc.core.dto.NodeData;


import static org.assertj.core.api.InstanceOfAssertFactories.PREDICATE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import com.dev.ssc.infrastructure.file.CsvNodeLoader;
import com.dev.ssc.infrastructure.file.NodeDataCsvLoaders;
import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
//import org.apache.logging.log4j.LogManager;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Node;

import java.util.List;


@SpringBootTest
class TestCSV {

    @Autowired
    private final List<NodeData> forSpatialNodes;

//    private static final Logger logger = LoggerFactory.getLogger(com.dev.ssc.TestCSV.class);
    private static final Logger logger = LoggerFactory.getLogger(TestCSV.class);

    @Autowired
    TestCSV(List<NodeData> forSpatialNodes) {
        this.forSpatialNodes = forSpatialNodes;
    }


    @Test
    public void read() {

//        CsvNodeLoader csvNodeLoader = new CsvNodeLoader();
//        NodeDataCsvLoaders nodeDataCsvLoaders = new N

       logger.info("com.dev.ssc.TestCSV Return :" + forSpatialNodes);
//
//        logger.info("index : " + forSpatialNodes.get(4));
        //assertTrue(forSpatialNodes.toString().contains("투썸"));
//        assertTrue(forSpatialNodes.contains("test"));


        assertThat(forSpatialNodes)
                .flatMap(NodeData::metaNodes)
                .extracting(NodeData.MetaNode::name)
                .contains("투썸");


       // 실증 검증 로직 Assert를 통해 세부 검증 필요.
    }

}
