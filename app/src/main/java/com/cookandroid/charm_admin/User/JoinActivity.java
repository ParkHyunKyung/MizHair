package com.cookandroid.charm_admin.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * 회원가입 화면
 * 사용자의 아이디, 비밀번호 및 비밀번호 체크, 이름을 입력받아 회원가입을 한다.
 */
public class JoinActivity extends Activity {

    private Button btnOK, btnCancel ; //회원가입 완료, 취소 버튼
    private EditText edtId, edtName, edtPassword, edtPasswordCheck,edtPhone; //아이디, 이름, 비밀번호 및 비밀번호 재확인
    private RadioGroup rgGender;
    private CheckBox checkboxPrivacy;//개인정보 수집 체크박스
    private TextView txtPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //Xml 변수들에 대입
        btnOK = (Button) findViewById(R.id.join_btnOK);
        btnCancel = (Button) findViewById(R.id.join_btnCancel);
        edtId = (EditText) findViewById(R.id.join_edtId);
        edtName = (EditText) findViewById(R.id.join_edtName);
        edtPhone = (EditText)findViewById(R.id.join_edtPhone);
        rgGender = (RadioGroup) findViewById(R.id.join_rgGender);
        edtPassword = (EditText) findViewById(R.id.join_edtPassword);
        edtPasswordCheck = (EditText) findViewById(R.id.join_edtPasswordCheck);
        checkboxPrivacy = (CheckBox)findViewById(R.id.join_checkboxPrivacy);
        txtPrivacy = (TextView)findViewById(R.id.join_txtPrivacy);

        checkPrivacy();
        //회원가입 버튼 클릭 시 제약조건 확인 및 회원가입 완료
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtId.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if (edtName.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if (edtPhone.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "연락처를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if (edtPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!edtPassword.getText().toString().equals(edtPasswordCheck.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 체크해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!checkboxPrivacy.isChecked()) {
                    Toast.makeText(getApplicationContext(), "개인정보 수집 및 이용에 동의해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "미구현", Toast.LENGTH_SHORT).show();
                    int id = rgGender.getCheckedRadioButtonId();
                    int genderNum = 0;
                    RadioButton radioButton = (RadioButton)findViewById(id);
                    if(radioButton.getText().toString().trim().equals("여성")){
                        genderNum = 0;
                    }else if(radioButton.getText().toString().trim().equals("남성")){
                        genderNum = 1;
                    }else{

                    }

                    Toast.makeText(getApplicationContext(),edtName.getText().toString().trim()+edtPhone.getText().toString().trim()+radioButton.getText().toString().trim()+Integer.toString(genderNum)+edtPassword.getText().toString(),Toast.LENGTH_SHORT).show();
                    //회원가입 메소드가 구현 완료시 사용
                    signUp(edtName.getText().toString().trim(), edtPhone.getText().toString().trim(),Integer.toString(genderNum).trim(),edtId.getText().toString().trim(), edtPassword.getText().toString());
                }
            }
        });

        //취소 시 로그인 화면을 출력
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    //회원가입이 서버가 되면 실행

    private void checkPrivacy(){
        txtPrivacy.setText("<미즈헤어>('www.mizhair.ga'이하 '미즈헤어')은(는) 개인정보보호법에 따라 이용자의 개인정보 보호 및 권익을 보호하고 개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리방침을 두고 있습니다.\n" +
                "\n" +
                "<미즈헤어>('미즈헤어') 은(는) 회사는 개인정보처리방침을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다.\n" +
                "\n" +
                "○ 본 방침은부터 2016년 1월 1일부터 시행됩니다.\n" +
                "\n" +
                "\n" +
                "1. 개인정보의 처리 목적 <미즈헤어>('www.mizhair.ga'이하 '미즈헤어')은(는) 개인정보를 다음의 목적을 위해 처리합니다. 처리한 개인정보는 다음의 목적이외의 용도로는 사용되지 않으며 이용 목적이 변경될 시에는 사전동의를 구할 예정입니다.\n" +
                "\n" +
                "가. 홈페이지 회원가입 및 관리\n" +
                "\n" +
                "회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원자격 유지·관리, 서비스 부정이용 방지 등을 목적으로 개인정보를 처리합니다.\n" +
                "\n" +
                "\n" +
                "나. 마케팅 및 광고에의 활용\n" +
                "\n" +
                "신규 서비스(제품) 개발 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공 등을 목적으로 개인정보를 처리합니다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "2. 개인정보 파일 현황\n" +
                "('www.mizhair.ga'이하 '미즈헤어')가 개인정보 보호법 제32조에 따라 등록․공개하는 개인정보파일의 처리목적은 다음과 같습니다.\n" +
                "\n" +
                "1. 개인정보 파일명 : 개인정보처리방침\n" +
                "- 개인정보 항목 : 휴대전화번호, 비밀번호, 로그인ID, 성별, 생년월일, 이름\n" +
                "- 수집방법 : 앱\n" +
                "- 보유근거 : 회원정보유지\n" +
                "- 보유기간 : 3년\n" +
                "- 관련법령 : 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년\n" +
                "\n" +
                "\n" +
                "\n" +
                "※ 기타('www.mizhair.ga'이하 '미즈헤어')의 개인정보파일 등록사항 공개는 행정안전부 개인정보보호 종합지원 포털(www.privacy.go.kr) → 개인정보민원 → 개인정보열람등 요구 → 개인정보파일 목록검색 메뉴를 활용해주시기 바랍니다.\n" +
                "\n" +
                "3. 개인정보의 처리 및 보유 기간\n" +
                "\n" +
                "① <미즈헤어>('미즈헤어')은(는) 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집시에 동의 받은 개인정보 보유,이용기간 내에서 개인정보를 처리,보유합니다.\n" +
                "\n" +
                "② 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.\n" +
                "\n" +
                "1.<홈페이지 회원가입 및 관리>\n" +
                "<홈페이지 회원가입 및 관리>와 관련한 개인정보는 수집.이용에 관한 동의일로부터<1년>까지 위 이용목적을 위하여 보유.이용됩니다.\n" +
                "-보유근거 : 회원정보유지\n" +
                "-관련법령 : 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년\n" +
                "-예외사유 : 동의거절\n" +
                "\n" +
                "\n" +
                "\n" +
                "4. 개인정보의 제3자 제공에 관한 사항\n" +
                "\n" +
                "① <미즈헤어>('www.mizhair.ga'이하 '미즈헤어')은(는) 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.\n" +
                "\n" +
                "② <미즈헤어>('www.mizhair.ga')은(는) 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.\n" +
                "\n" +
                "\n" +
                "1. <사업주>\n" +
                "- 개인정보를 제공받는 자 : 사업주\n" +
                "- 제공받는 자의 개인정보 이용목적 : 이메일, 휴대전화번호, 비밀번호, 로그인ID, 성별, 생년월일, 이름\n" +
                "- 제공받는 자의 보유.이용기간:\n" +
                "\n" +
                "\n" +
                "\n" +
                "5. 개인정보처리 위탁\n" +
                "\n" +
                "① <미즈헤어>('미즈헤어')은(는) 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.\n" +
                "\n" +
                "1. <>\n" +
                "- 위탁받는 자 (수탁자) : \n" +
                "- 위탁하는 업무의 내용 : \n" +
                "- 위탁기간 :\n" +
                "\n" +
                "\n" +
                "\n" +
                "② <미즈헤어>('www.mizhair.ga'이하 '미즈헤어')은(는) 위탁계약 체결시 개인정보 보호법 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적․관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리․감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.\n" +
                "\n" +
                "③ 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.\n" +
                "\n" +
                "\n" +
                "6. 정보주체의 권리,의무 및 그 행사방법 이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.\n" +
                "\n" +
                "① 정보주체는 미즈헤어(‘www.mizhair.ga’이하 ‘미즈헤어) 에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.\n" +
                "1. 개인정보 열람요구\n" +
                "2. 오류 등이 있을 경우 정정 요구\n" +
                "3. 삭제요구\n" +
                "4. 처리정지 요구\n" +
                "② 제1항에 따른 권리 행사는미즈헤어(‘www.mizhair.ga’이하 ‘미즈헤어) 에 대해 개인정보 보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 <기관/회사명>(‘사이트URL’이하 ‘사이트명) 은(는) 이에 대해 지체 없이 조치하겠습니다.\n" +
                "③ 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 <기관/회사명>(‘사이트URL’이하 ‘사이트명) 은(는) 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.\n" +
                "④ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "7. 처리하는 개인정보의 항목 작성 \n" +
                "\n" +
                "① <미즈헤어>('www.mizhair.ga'이하 '미즈헤어')은(는) 다음의 개인정보 항목을 처리하고 있습니다.\n" +
                "\n" +
                "1<홈페이지 회원가입 및 관리>\n" +
                "- 필수항목 : 휴대전화번호, 비밀번호, 로그인ID, 성별, 생년월일, 이름\n" +
                "- 선택항목 :\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "8. 개인정보의 파기<미즈헤어>('미즈헤어')은(는) 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.\n" +
                "\n" +
                "-파기절차이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.-파기기한이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.\n" +
                "\n" +
                "-파기방법\n" +
                "시스템관리자가 파기\n" +
                "\n" +
                "\n" +
                "\n" +
                "9. 개인정보의 안전성 확보 조치 <미즈헤어>('미즈헤어')은(는) 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습니다.\n" +
                "\n" +
                "1. 개인정보 취급 직원의 최소화 및 교육\n" +
                "개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화 하여 개인정보를 관리하는 대책을 시행하고 있습니다.\n" +
                "\n" +
                "2. 해킹 등에 대비한 기술적 대책\n" +
                "<미즈헤어>('미즈헤어')은 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신·점검을 하며 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적/물리적으로 감시 및 차단하고 있습니다.\n" +
                "\n" +
                "3. 접속기록의 보관 및 위변조 방지\n" +
                "개인정보처리시스템에 접속한 기록을 최소 6개월 이상 보관, 관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않도록 보안기능 사용하고 있습니다.\n" +
                "\n" +
                "4. 개인정보에 대한 접근 제한\n" +
                "개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "10. 개인정보 보호책임자 작성\n" +
                "\n" +
                "\n" +
                "① 미즈헤어(‘www.mizhair.ga’이하 ‘미즈헤어) 은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.\n" +
                "\n" +
                "▶ 개인정보 보호책임자 \n" +
                "성명 :이종희\n" +
                "직책 :팀장\n" +
                "직급 :팀장\n" +
                "연락처 :01077304951, mizhair2016@naver.com, \n" +
                "※ 개인정보 보호 담당부서로 연결됩니다.\n" +
                "\n" +
                "▶ 개인정보 보호 담당부서\n" +
                "부서명 :미즈헤어\n" +
                "담당자 :이종희\n" +
                "연락처 :01077304951, mizhair2016@naver.com, \n" +
                "② 정보주체께서는 미즈헤어(‘www.mizhair.ga’이하 ‘미즈헤어) 의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 미즈헤어(‘www.mizhair.ga’이하 ‘미즈헤어) 은(는) 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "11. 개인정보 처리방침 변경\n" +
                "\n" +
                "①이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.");
    }

    private void signUp(String name,String phone,String gender, String id, String pw) {
        try {
            String SingupServer = "http://mizhair.ga/SignUp.php?";
            name = URLEncoder.encode(name, "UTF-8");
            id = URLEncoder.encode(id, "UTF-8");
            pw = URLEncoder.encode(pw, "UTF-8");
            phone = URLEncoder.encode(phone, "UTF-8");
            gender = URLEncoder.encode(gender, "UTF-8");
            SingupServer += "UserName=";
            SingupServer += name;
            SingupServer += "&UserID=";
            SingupServer += id;
            SingupServer += "&UserPass=";
            SingupServer += pw;
            SingupServer += "&UserPhone=";
            SingupServer += phone;
            SingupServer += "&UserGender=";
            SingupServer += gender;
            URLConnector task = new URLConnector(SingupServer);
            task.start();

            task.join();
            String result = task.getResult();
            JSONObject state = new JSONObject(result);
            if (state.getString("STATE").equals("1")) {
                Toast.makeText(getApplicationContext(), "중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                return;
            } else if (state.getString("STATE").equals("0")) {
                Toast.makeText(getApplicationContext(), "회원가입 완료!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
