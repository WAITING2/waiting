package com.xing.paper.service.impl;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.xing.paper.service.WordService;
import org.springframework.stereotype.Service;

/**
 * 通过aspose库转换
 *
 * @author Felix Yan
 * @data 2018/07/16
 */
@Service("asposeWordService")
public class AsposeWordServiceImpl implements WordService {

    @Override
    public void word2pdf(String inPath, String outPath) throws Exception {
        Document doc = new Document(inPath);
        doc.save(outPath, SaveFormat.PDF);
    }
}