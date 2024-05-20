package hgcq.controller;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import hgcq.callback.LoginCallback;
import hgcq.callback.MemberCallback;
import hgcq.config.NetworkClient;
import hgcq.model.dto.MemberDTO;
import hgcq.model.service.MemberService;
import hgcq.model.service.PhotoService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MemberController {

    private NetworkClient client;
    private MemberService memberService;
    private Context context;


    public MemberController(Context context) {
        client = NetworkClient.getInstance(context.getApplicationContext());
        memberService = client.getMemberService();
        this.context = context;
    }

    public void createMember(MemberDTO memberDto) {
        Call<ResponseBody> call = memberService.joinMember(memberDto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "회원 가입 성공!", Toast.LENGTH_SHORT).show();
                    Log.d("회원 가입 성공!", "성공" + response.code());
                } else {
                    Toast.makeText(context, "회원 가입 실패!", Toast.LENGTH_SHORT).show();
                    Log.e("회원 가입 실패!", "에러:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "회원가입:서버에서 응답받지 못했어용 ㅠㅠ", Toast.LENGTH_SHORT).show();
                Log.e("회원가입:서버에서 응답받지 못했어용 ㅠㅠ", t.getMessage());
            }
        });
    }

    public void loginMember(MemberDTO memberDto, LoginCallback callback) {
        Call<ResponseBody> call = memberService.loginMember(memberDto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Log.d("로그인 성공!", "성공" + response.code());
                    callback.onSuccess(true);
                    client.saveCookie();
                } else {
                    Toast.makeText(context, "로그인 실패ㅜㅜ", Toast.LENGTH_SHORT).show();
                    Log.e("로그인 실패ㅜㅜ", "에러:" + response.code());
                    callback.onSuccess(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "사진 업로드 성공", Toast.LENGTH_SHORT).show();
                Log.e("로그인:서버에서 응답받지 못했어용ㅜㅜ", t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }

    //updateMember
    public void updateMember(MemberDTO memberDto) {
        Call<ResponseBody> call = memberService.updateMember(memberDto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "회원 정보 수정 완료!", Toast.LENGTH_SHORT).show();
                    Log.d("회원 정보 수정 완료!", "성공" + response.code());
                } else {
                    Toast.makeText(context, "회원 정보 수정 실패 ㅜㅜ", Toast.LENGTH_SHORT).show();
                    Log.e("회원 정보 수정 실패 ㅜㅜ", "에러:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "회원 정보 수정:서버에서 응답받지 못했어용", Toast.LENGTH_SHORT).show();
                Log.e("회원 정보 수정:서버에서 응답받지 못했어용", t.getMessage());
            }
        });
    }

    public void logoutMember() {
        Call<ResponseBody> call = memberService.logoutMember();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "로그 아웃 성공!", Toast.LENGTH_SHORT).show();
                    Log.d("로그 아웃 성공!", "성공" + response.code());
                    client.deleteCookie();
                } else {
                    Toast.makeText(context, "로그아웃  실패!", Toast.LENGTH_SHORT).show();
                    Log.e("로그아웃  실패!", "에러:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "로그아웃:서버에서 응답받지 못했어용 ㅠㅠ", Toast.LENGTH_SHORT).show();
                Log.e("로그아웃:서버에서 응답받지 못했어용 ㅠㅠ", t.getMessage());
            }
        });
    }

    public void addFriend(MemberDTO memberDto) {
        //
        Call<ResponseBody> call = memberService.addFriend(memberDto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "친구 추가 성공!", Toast.LENGTH_SHORT).show();
                    Log.d("친구 추가 성공!", "성공" + response.code());
                } else {
                    Toast.makeText(context, "친구 추가 실패!", Toast.LENGTH_SHORT).show();
                    Log.e("친구 추가 실패!", "에러:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "친구 추가:서버에서 응답받지 못했어용 ㅠㅠ", Toast.LENGTH_SHORT).show();
                Log.e("친구 추가:서버에서 응답받지 못했어용 ㅠㅠ", t.getMessage());
            }
        });
    }

    public void deleteFriend(MemberDTO memberDto) {
        Call<ResponseBody> call = memberService.deleteFriend(memberDto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "친구 삭제 성공!", Toast.LENGTH_SHORT).show();
                    Log.d("친구 삭제 성공!", "성공" + response.code());
                } else {
                    Toast.makeText(context, "친구 삭제 실패!", Toast.LENGTH_SHORT).show();
                    Log.e("친구 삭제 실패!", "에러:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "친구 삭제:서버에서 응답받지 못했어용 ㅠㅠ", Toast.LENGTH_SHORT).show();
                Log.e("친구 삭제:서버에서 응답받지 못했어용 ㅠㅠ", t.getMessage());
            }
        });
    }

    public void friendList(MemberCallback callback) {
        Call<List<MemberDTO>> call = memberService.friendList();

        call.enqueue(new Callback<List<MemberDTO>>() {
            @Override
            public void onResponse(Call<List<MemberDTO>> call, Response<List<MemberDTO>> response) {
                if (response.isSuccessful()) {
                    Log.d("친구리스트 조회 성공", "성공" + response.code());
                    callback.onSuccess(response.body());
                } else {
                    Log.e("친구리스트 조회 실패", "에러:" + response.code());
                    callback.onError("에러:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MemberDTO>> call, Throwable t) {
                Log.e("친구리스트 조회:서버에서 응답받지 못했어용 ㅠㅠ", t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }
}