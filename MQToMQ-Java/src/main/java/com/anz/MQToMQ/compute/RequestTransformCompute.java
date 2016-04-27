/**
 * 
 */
package com.anz.MQToMQ.compute;

import com.anz.MQToMQ.transform.PreTransformBLSample;

import com.anz.common.compute.impl.CommonJsonJsonTransformCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public class RequestTransformCompute extends CommonJsonJsonTransformCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	@Override
	public ITransformer<String, String> getTransformer() {
		return new PreTransformBLSample();
	}

	@Override
	public void saveUserProvidedProperties(MbMessageAssembly outAssembly) {
		// TODO Auto-generated method stub
		
	}

}
