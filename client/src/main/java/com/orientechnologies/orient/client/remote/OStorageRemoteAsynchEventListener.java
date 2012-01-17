package com.orientechnologies.orient.client.remote;

import com.orientechnologies.orient.core.record.ORecordInternal;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.enterprise.channel.binary.OChannelBinaryProtocol;
import com.orientechnologies.orient.enterprise.channel.binary.ORemoteServerEventListener;
import com.orientechnologies.orient.enterprise.channel.distributed.OChannelDistributedProtocol;

public class OStorageRemoteAsynchEventListener implements ORemoteServerEventListener {

	private OStorageRemote	storage;

	public OStorageRemoteAsynchEventListener(OStorageRemote storage) {
		this.storage = storage;
	}

	public void onRequest(final byte iRequestCode, final Object obj) {
		if (iRequestCode == OChannelBinaryProtocol.REQUEST_PUSH_RECORD)
			// ASYNCHRONOUS PUSH INTO THE LEVEL2 CACHE
			storage.getLevel2Cache().updateRecord((ORecordInternal<?>) obj);
		else if (iRequestCode == OChannelDistributedProtocol.PUSH_DISTRIBUTED_CONFIG)
			storage.updateClusterConfiguration((ODocument) obj);
	}
}
