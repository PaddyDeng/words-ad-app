package thinku.com.word.ui.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinku.com.word.R;
import thinku.com.word.base.BaseActivity;

public class UserActivity extends BaseActivity {
    private TextView content  ,title;
    private ImageView back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        title.setText("用户协议");
        content.setText("雷哥网用户注册协议\n" +
                "\n" +
                "特别提示\n" +
                "雷哥网在此特别提醒您（用户）在注册成为用户之前，请认真阅读本《用户协议》（以下简称“协议”），以确保您充分理解本协议中各条款。您的注册、登录、使用等行为将视为对本协议的接受，并同意接受本协议各项条款的约束。\n" +
                "本协议约定雷哥网与用户之间关于“雷哥单词”应用软件服务使用的权利义务。本协议可由雷哥网随时更新，更新后的协议条款一旦公布即代替原来的协议条款，恕不再另行通知，用户继续使用雷哥单词应用软件提供的服务将被视为接受修改后的协议，用户可在本网站查阅最新版协议条款。\n" +
                "\n" +
                "一、帐号注册\n" +
                "1.用户注册成功后即成为雷哥网的合法用户，注册成功后会得到一个账号密码，用户用该账号进行做题等一切活动，并对自己的行为负责，由此行为产生的一切行为后果均由自己承担，雷哥网不承担任何责任。\n" +
                "2.在用户注册及雷哥网搜集的信息包括但不限于用户的昵称、电话、邮箱和密码；雷哥网同意对这些信息的使用将受限于第三条用户个人隐私信息保护的约束。\n" +
                "\n" +
                "二、用户承诺\n" +
                "用户同意遵守《中华人民共和国保守国家秘密法》、《中华人民共和国著作权法》、《互联网电子公告服务管理规定》、《信息网络传播法保护条例》、《中华人民共和国计算机信息系统安全保护条例》等有关法律法规及政府部门的规定，一经发现，雷哥网将根据法律法规采取相应措施。\n" +
                "\n" +
                "三、免责声明\n" +
                "雷哥单词APP为互联网服务提供商，为单词学习用户提供在线考试学习服务及沟通交流平台，根据《侵权责任法》以及《信息网络传播权保护条例》规定，对于用户个人的侵权及违法行为，雷哥网不承担民事责任。\n" );
    }

    public void initView(){
        content = (TextView) findViewById(R.id.content);
        title = (TextView) findViewById(R.id.title_t);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

    }
}
