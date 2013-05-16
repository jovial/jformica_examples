package org.cowboycoders.ant.examples;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

import org.cowboycoders.ant.Channel;
import org.cowboycoders.ant.NetworkKey;
import org.cowboycoders.ant.Node;
import org.cowboycoders.ant.events.BroadcastListener;
import org.cowboycoders.ant.interfaces.AntTransceiver;
import org.cowboycoders.ant.messages.ChannelType;
import org.cowboycoders.ant.messages.SlaveChannelType;
import org.cowboycoders.ant.messages.data.BroadcastDataMessage;

class Listener implements BroadcastListener<BroadcastDataMessage> {

	@Override
	public void receiveMessage(BroadcastDataMessage message) {
		System.out.println("Heart rate: " + message.getData()[7]);
	}

}

public class BasicHeartRateMonitor {
	
	public static final Level LOG_LEVEL = Level.SEVERE;

	public static void main(String[] args) throws InterruptedException {
		// first usb ant-stick
		AntTransceiver antchip = new AntTransceiver(0);
		
		// set logging level
	    AntTransceiver.LOGGER.setLevel(LOG_LEVEL);
	    ConsoleHandler handler = new ConsoleHandler();
	    // PUBLISH this level
	    handler.setLevel(LOG_LEVEL);
	    AntTransceiver.LOGGER.addHandler(handler);
		
		antchip.start();
		
		Node node = new Node(antchip);

		NetworkKey key = new NetworkKey(0xB9,0xA5,0x21,0xFB,0xBD,0x72,0xC3,0x45);
		key.setName("N:ANT+");

		node.start();
		node.reset();

		// sets network key of network zero
		node.setNetworkKey(0, key);

		Channel channel = node.getFreeChannel();
		
		// Arbitrary name : useful for identifying channel
		channel.setName("C:HRM");

		ChannelType channelType = new SlaveChannelType();
		
		// use ant network key "N:ANT+" 
		channel.assign("N:ANT+", channelType);
		
		channel.registerRxListener(new Listener(), BroadcastDataMessage.class);

		channel.setId(0, 120, 0, false);

		channel.setFrequency(57);

		channel.setPeriod(8070);
		
		channel.setSearchTimeout(Channel.SEARCH_TIMEOUT_NEVER);

		channel.open();
		
		// Listen for 60 seconds
		Thread.sleep(60000);

		channel.close();
		channel.unassign();

		//return the channel to the pool of available channels
		node.freeChannel(channel);

		node.stop();
		
	}
	


}
