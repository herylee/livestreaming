package edu.sysu.netlab.livestreaming.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

import edu.sysu.netlab.livestreaming.controller.GameTypeController;
import edu.sysu.netlab.livestreaming.controller.IndexController;
import edu.sysu.netlab.livestreaming.controller.RoomController;
import edu.sysu.netlab.livestreaming.controller.UserController;
import edu.sysu.netlab.livestreaming.model.GameType;
import edu.sysu.netlab.livestreaming.model.LiveRoom;
import edu.sysu.netlab.livestreaming.model.RecordRoom;
import edu.sysu.netlab.livestreaming.model.User;

public class JFinalProjectConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
		me.add("/user", UserController.class);
		me.add("/room", RoomController.class);
		me.add("/gameType", GameTypeController.class);
		
	}

	@Override
	public void configPlugin(Plugins me) {
		this.loadPropertyFile("config.properties");
		
		//---druid
		DruidPlugin dp = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password"));
		me.add(dp);
		//---End of druid
		
		//---AR
		ActiveRecordPlugin activePlugin = new ActiveRecordPlugin(dp);
		me.add(activePlugin);
		activePlugin.addMapping("User", User.class);
		activePlugin.addMapping("LiveRoom", LiveRoom.class);
		activePlugin.addMapping("RecordRoom", RecordRoom.class);
		activePlugin.addMapping("GameType", GameType.class);
		//---End of AR
	}

	@Override
	public void configInterceptor(Interceptors me) {
	}

	@Override
	public void configHandler(Handlers me) {
	}
	
	@Override
	public void afterJFinalStart() {
	}
	
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8080, "/", 5);
	}
		
}
