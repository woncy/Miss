package cn.wangxi.miss_client.activity.net;

import org.java_websocket.handshake.ServerHandshake;

import chat.message.AbstractMessage;
import chat.message.impl.ChatMessage;

public abstract class MessageHandler{
	protected Client client;
    public void onOpen(ServerHandshake sh) {
        System.out.println("打开链接");
    }

    public void onError(Exception e) {
        e.printStackTrace();
        System.out.println("发生错误已关闭");
    }

    public void onClose(int arg0, String arg1, boolean arg2) {
        System.out.println("链接已关闭");
    }

//    public abstract void onMessage(Message message);
    
    public abstract void onMessage(AbstractMessage msg);

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	
    
    
    

}
