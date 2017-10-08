package cn.wangxi.miss_client.activity.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import chat.core.JsonUtil;
import chat.message.AbstractMessage;
import chat.message.MessageException;
import chat.message.MessageManager;
import cn.wangxi.miss_client.R;

public class Client {

	public  final String uri = "ws://"+R.string.server_url+":"+R.string.server_port+"/g";
	WebSocketClient client;
	MessageHandler handler;
	
	public Client(MessageHandler handler) throws URISyntaxException {
		super();
		this.handler = handler;
		initClient();
	}
	public void setHandler(MessageHandler handler) {
		this.handler = handler;
	}
	private void initClient() throws URISyntaxException{
		client = new WebSocketClient(new URI(uri),new Draft_17()) {
	        @Override
	        public void onOpen(ServerHandshake sh) {
	           handler.onOpen(sh);
	        }
	        @Override
	        public void onMessage(String msg) {
	        	org.json.JSONObject obj = null;
				try {
					obj = new JSONObject(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        	int messageId = -1;
				try {
					messageId = obj.getInt("messageId");
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        	AbstractMessage message = null;
				try {
					message = MessageManager.getInstance().getMessageById(messageId);
				} catch (MessageException e) {
					e.printStackTrace();
				}
	        	try {
					message = (AbstractMessage) JsonUtil.jsonToJavaBean(obj, message.getClass());
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	System.out.println("收到消息:"+message);
	        	handler.onMessage(message);
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
//	           try {
//					String strMsg = new String(bytes.array(),"utf-8");
//					JSONObject obj = new JSONObject(strMsg);
//					int messageId = obj.getInt("messageId");
//					
//					AbstractMessage msg = (AbstractMessage) JsonUtil.jsonToJavaBean(obj, MessageManager.getInstance().getMessageById(messageId).getClass());
//					handler.onMessage(msg);
//				}  catch (Exception | MessageException e) {
//					e.printStackTrace();
//				}
        	}

		};
		client.connect();
	}
	
	public void send(AbstractMessage msg){
		System.out.println("发送消息:"+msg);
		this.send(new JSONObject(msg).toString());
	}
	public void send(String msg){
		client.send(msg);
	}
	
	
//	public void sendMessage(Message message) throws NotYetConnectedException, IOException, ProtocolException{
//		client.send(write(message));
//	}
//	private static byte[] write(Message msg) throws IOException, ProtocolException
//    {
//	
//		ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer();
//		buffer.retain();
//        Output _out = MarkCompressOutput.create(buffer);
//        _out.writeInt(msg.getMessageType());
//        _out.writeInt(msg.getMessageId());
//        //_out.writeString(msg.toString());
//        msg.encode(_out);
//        byte[] byteArr = new byte[buffer.readableBytes()];
//        buffer.readBytes(byteArr);
////        bo.write(byteArr);
//        String msgString = msg.toString();
//        int type =msg.getMessageType();
//        int id = msg.getMessageId();
//        System.out.println("发送消息:"+msgString);
//        System.out.println("发送消息:[type="+type+",id="+id+"]");
//        return byteArr;
//        //websocket1.Send(byteArr);
//    }
}
