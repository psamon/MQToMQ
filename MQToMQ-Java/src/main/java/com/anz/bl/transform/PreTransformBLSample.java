/**
 * 
 */
package com.anz.bl.transform;

import org.apache.logging.log4j.LogManager;



import org.apache.logging.log4j.Logger;

import com.anz.bl.transform.pojo.NumbersInput;


import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.dataaccess.models.iib.Operation;
import com.anz.common.domain.OperationDomain;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;


/**
 * @author sanketsw
 * 
 */
public class PreTransformBLSample implements ITransformer<String, String> {

	private static final Logger logger = LogManager.getLogger();
	
	/* (non-Javadoc)
	 * @see com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 */
	public String execute(String inputJson, Logger logger, ComputeInfo metadata) throws Exception {
		NumbersInput json = (NumbersInput) TransformUtils.fromJSON(inputJson,
				NumbersInput.class);
		logger.info("Inside PreTransform");
		//throw new Exception("Error in request transform- user created");
		
/*		logger.info("json = {}", json);
		
		//Get Message ID
		String header = "MsgId";
		
		int startId = inputJson.indexOf(header) + header.length() + 1;
		int endId = inputJson.indexOf(header, startId) - 2;

		String msgId = inputJson.substring(startId, endId);
		
		logger.info("msgId = {}", msgId);
		
		
		//METHOD TO STORE IN CACHE
		CacheHandlerFactory.getInstance().updateCache("MqHeaderCache", "MqmdCorrelId", msgId);
		
		//METHOD TO RETRIEVE FROM CACHE
		String correlId = CacheHandlerFactory.getInstance().lookupCache("MQHeaderCache", "MQMDCorrelId");
		
		logger.info("CorrelId = {}", correlId);*/
		
		json.setLeft(json.getLeft() + 100);
		String out = TransformUtils.toJSON(json);
		return out;
	}


}
