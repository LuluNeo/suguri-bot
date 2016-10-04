package suguriBot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SuguriBot extends TelegramLongPollingBot
{
	public static final String bot_username = "SuguriPictureBot";
	public static final String bot_token = "272076469:AAEVjXmUOdBq54AyjTDyTbANOLD_Xp63rBI";
	public static ObjectMapper mapper = new ObjectMapper();
	public static ArrayList<String> list = new ArrayList<String>();

	@Override
	public String getBotUsername() 
	{
		return bot_username;
	}

	@Override
	public void onUpdateReceived(Update update)
	{
		if(update.getMessage().getPhoto() != null && update.getMessage().getChat().isUserChat() && update.getMessage().getFrom().getUserName().equals("uw0tm8y"))
		{	
			list.add(update.getMessage().getPhoto().get(0).getFileId());
		}
		try {
			mapper.writeValue(new File("data.json"), list);
		} catch (IOException e) {
			e.printStackTrace();	
		}
		try{
			if(update.getMessage().getText() != null && (update.getMessage().getText().equals("/suguri")||update.getMessage().getText().equals("/suguri@SuguriPictureBot")))
			{
				this.sendPhoto(new SendPhoto().setChatId(update.getMessage().getChatId()+"").setPhoto(list.get((int)(Math.random()*list.size()))));
			}
		}catch (Exception e){
		e.printStackTrace();
		}
	}

	@Override
	public String getBotToken()
	{
		return bot_token;
	}
	
	public static void main(String[] args) throws Exception
	{
		try{
			list = mapper.readValue(new File("data.json"), ArrayList.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		TelegramBotsApi api = new TelegramBotsApi();
		SuguriBot bot = new SuguriBot();
		api.registerBot(bot);
		/*Runtime.getRuntime().addShutdownHook(new Thread(()->{
		try{
			mapper.writeValue(new File("data.json"), list);
		}catch(Exception e){
			e.printStackTrace();
			}
		}));*/
	}
}