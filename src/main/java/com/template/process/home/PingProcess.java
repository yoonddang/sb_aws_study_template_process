package com.template.process.home;

import com.template.common.model.PingModel;
import com.template.common.reststub.ping.PingStubBO;
import com.template.repository.sample.SampleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ping")
public class PingProcess implements PingStubBO {
    private static final Logger logger = LoggerFactory.getLogger(PingProcess.class);

    @Autowired
    private SampleDAO sampleDAO;

    @RequestMapping(value = "/ping", method = RequestMethod.POST)
    @ResponseBody
    public PingModel ping(@RequestBody PingModel map) {
        int test = sampleDAO.selectOne();
        map.setSample(map.getSample()  + ":" + test);
        logger.debug("ping({})", map.getSample());

        return map;
    }
}
