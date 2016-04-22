/**
 * 
 */
package com.anz.error;

import org.apache.logging.log4j.Logger;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.dataaccess.models.iib.IFXCode;
import com.anz.common.domain.IFXCodeDomain;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 * 
 */
public class TransformFailureResponse implements
		ITransformer<MbMessageAssembly, String> {

	@Override
	public String execute(MbMessageAssembly outAssembly, Logger logger, ComputeInfo metadata) throws Exception {
		String out = null;
		
		// This is internal failure in transformation or logic		
		MbElement exception = outAssembly.getExceptionList().getRootElement().getFirstChild();	
		logger.error("exception {} ",exception);
		String exceptionText = null;
		while (exception != null) {	
			if(exceptionText == null) {
				exceptionText = "Error";
			}
			MbElement insert = exception.getFirstElementByPath("Insert");
			while(insert != null) {
				String text = (String)insert.getFirstElementByPath("Text").getValue();			
				if(text != null && !text.isEmpty()) {
					exceptionText = exceptionText + ": " + text;
				}
				insert = insert.getNextSibling();
			}
			if(exception.getNextSibling() != null) {
				exception = exception.getNextSibling();
			} else {
				exception = exception.getFirstChild();
			}
		}
		logger.error("exceptionText {} ",exceptionText);
		
		// This could be the business or HTTP Request  exception
		
		MbMessage inMessage = outAssembly.getMessage();
		String inputString = ComputeUtils.getStringFromBlob(inMessage);

		// Log the input blob
		logger.error("inputString {} ", inputString);		

		// Return the error after mapping errorCode from cache/database
		IFXCode errorCode = IFXCodeDomain.getInstance().getErrorCode("BL305657E");
		
		// Form the error object to return back
		String errorString = (exceptionText!=null? exceptionText: "");
		errorString = errorString + (inputString != null? inputString : "");

		// If error code cannot be mapped, then return the original error
		if (errorCode == null) {
			out = errorString;
			logger.info("passing the error over as it is {} ", out);
		} else {
			errorCode.setDescr(errorString);
			out = TransformUtils.toJSON(errorCode);
			logger.info("got the error code object from static data: {}", out);
		}
		return out;
	}

}
