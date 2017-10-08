package com.gunjin.util.testMessage.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import com.isnowfox.core.io.MarkCompressOutput;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.Message;

import cn.wangxi.miss_client.R;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import mj.net.message.proxy.ProxyAbstractMessage;
import mj.net.message.proxy.ProxyMessageManager;

public class Client {

	public  final String uri = "ws://"+R.string.server_url+":"+R.string.server_port+"/g";
	WebSocketClient client;
	MessageHandler handler;
	private int locationIndex;
	
	public Client(MessageHandler handler) throws URISyntaxException {
		super();
		this.handler = handler;
		initClient();
	}


	public void setHandler(MessageHandler handler) {
		this.handler = handler;
	}

	public int getLocationIndex() {
		return locationIndex;
	}


	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}


	private void initClient() throws URISyntaxException{
		client = new WebSocketClient(new URI(uri),new Draft_17()) {
	
	
	        @Override
	        public void onOpen(ServerHandshake sh) {
	           handler.onOpen(sh);
	        }
	
	        @Override
	        public void onMessage(String msg) {
	        	
	        	JSONObject obj;
				try {
					obj = new JSONObject(msg);
					Class msgClazz = ProxyMessageManager.getMessageById(obj.getInt("messageId"));
					ProxyAbstractMessage message = (ProxyAbstractMessage) ProxyMessageManager.jsonToJavaBean(obj,msgClazz);
		        	System.out.println("收到服务器消息:{}"+obj);
		        	handler.onMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				} 
	        	
	        }
	
	        @Override
	        public void onError(Exception e) {
	        	handler.onError(e);
	        }
	
	        @Override
	        public void onClose(int arg0, String arg1, boolean arg2) {
	        	handler.onClose(arg0, arg1, arg2);
	        }
	
	        @Override
	        public void onMessage(ByteBuffer bytes) {
	            try {
					String msg = new String(bytes.array(),"utf-8");
					System.out.println(msg);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				MarkCompressInput input = (MarkCompressInput) MarkCompressInput.create(buffer);
//				try {
//					int type = input.readInt();
//					int id = input.readInt();
//					Message message = MessageFactoryImpi.getInstance().getMessage(type, id);
//					message.decode(input);
//					System.out.println("收到消息:"+message.toString());
//					System.out.println("消息类型:[type="+type+" id="+id+"]");
//					handler.onMessage(message);
//				} catch (IOException e) {
//					// TODO 自动生成的 catch 块
//					e.printStackTrace();
//				} catch (ProtocolException e) {
//					// TODO 自动生成的 catch 块
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					// TODO 自动生成的 catch 块
//					e.printStackTrace();
//				}
        	}
        

		};
		
		
		client.connect();
	}
	
	
	public void sendMessage(Message message) throws NotYetConnectedException, IOException, ProtocolException{
		client.send(write(message));
	}
	
	public void sendMessage(String msg){
		client.send(msg);;
	}
	
	public void sendMessage(ProxyAbstractMessage msg){
		
		JSONObject obj = new JSONObject(msg);
		System.out.println("发送消息:{}"+obj);
		this.sendMessage(obj.toString());
	}
	
	private static byte[] write(Message msg) throws IOException, ProtocolException
    {
	
		ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer();
		buffer.retain();
        Output _out = MarkCompressOutput.create(buffer);
        _out.writeInt(msg.getMessageType());
        _out.writeInt(msg.getMessageId());
        //_out.writeString(msg.toString());
        msg.encode(_out);
        
        byte[] byteArr = new byte[buffer.readableBytes()];
        buffer.readBytes(byteArr);
//        bo.write(byteArr);
        String msgString = msg.toString();
        int type =msg.getMessageType();
        int id = msg.getMessageId();
        System.out.println("发送消息:"+msgString);
        System.out.println("发送消息:[type="+type+",id="+id+"]");
        return byteArr;
               
        //websocket1.Send(byteArr);
    }
	
	
	
}
