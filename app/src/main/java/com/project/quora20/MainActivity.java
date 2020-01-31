package com.project.quora20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.project.quora20.adapter.HomeAdapter;
import com.project.quora20.entity.Question;
import com.project.quora20.retrofit.QuoraRetrofitService;
import com.project.quora20.retrofit.RetrofitClientInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeAdapter.QuestionCommunication {
    private Toolbar toolbar;
    private SearchView searchView;
    private TextView userName;
    private TextView userEmail;
    private TextView userLevel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button newPostToolbar;
    private RecyclerView homeRecyclerView;
    private RecyclerView.Adapter homeAdapter;
    private RecyclerView.LayoutManager homeLayoutManager;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        sharedPreferences=getSharedPreferences("LoginData",MODE_PRIVATE);
        String userSP=sharedPreferences.getString("User","");
        String emailSP=sharedPreferences.getString("Email","");
        String levelSP=sharedPreferences.getString("Level","");
        View headerView = navigationView.getHeaderView(0);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("UserId","5e314d5e83f84b7add06ec38").apply();
        editor.putString("OrgId","").apply();
        editor.commit();

        String userId=sharedPreferences.getString("UserId","");
        System.out.println(userId+"MAIN ACTIVITY GUEST USERID");
        userName = (TextView) headerView.findViewById(R.id.nav_userName);
        userName.setText(userSP);
        userEmail=(TextView)headerView.findViewById(R.id.nav_userEmail);
        userEmail.setText(emailSP);
        userLevel=(TextView)headerView.findViewById(R.id.nav_level);
        userLevel.setText(levelSP);


        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar, R.string.openNav,
                R.string.closeNav
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Quora 2.0");
        toolbar.setSubtitle("Ask to know!");
//        toolbar.setLogo(R.drawable.ic_directions_run_black_24dp);

        newPostToolbar = (Button)findViewById(R.id.newPostToolbar);
        newPostToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPostIntent=new Intent(getApplicationContext(), NewPost.class);
                startActivity(newPostIntent);
                finish();
            }
        });

        searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),query.toString(),Toast.LENGTH_SHORT).show();
//                Intent searchIntent=new Intent(MainActivity.this,SearchResults.class);
//                searchIntent.putExtra("searchKey",query);
//                startActivity(searchIntent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        userId="5e3140bb4c951a1723dc3f01";
        QuoraRetrofitService quoraRetrofitService= RetrofitClientInstance.getRetrofitInstance().create(QuoraRetrofitService.class);
        Call<List<Question>> call=quoraRetrofitService.getAllQuestions(userId);
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                generateDataList(response.body());
                System.out.println("On Response Home getAllQuestions");
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println("OnFailure getAllQuestions"+t.getMessage());
            }
        });
    }

    private void generateDataList(List<Question>list){
        homeRecyclerView =findViewById(R.id.homeRecyclerView);
        //if list not null
        sharedPreferences=getSharedPreferences("LoginData",MODE_PRIVATE);
        String userId=sharedPreferences.getString("UserId","");
        homeAdapter =new HomeAdapter(list,MainActivity.this,userId);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
        homeRecyclerView.setLayoutManager(gridLayoutManager);
        homeRecyclerView.setAdapter(homeAdapter);
    }

    //View Answers to questions
    @Override
    public void onClick(Question question) {
        Intent qaIntent=new Intent( MainActivity.this, QuestionAnswer.class);
        qaIntent.putExtra("QID",question.getQuestionId());
        qaIntent.putExtra("OrgId",question.getOrgId());
        qaIntent.putExtra("QuesBody",question.getQuestionBody());
        qaIntent.putExtra("BestAns",question.getBestAnswerId());
        qaIntent.putExtra("CategoryId",question.getCategoryId());
        startActivity(qaIntent);
    }

    //ViewOrganization Intent
    @Override
    public void viewOrganization()
    {
        Intent organizationIntent=new Intent(this,ViewOrganisation.class);
        startActivity(organizationIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Toast.makeText(this, "this menu item clicked", Toast.LENGTH_SHORT).show();
        switch(item.getItemId()) {
            case R.id.sports_nav_menu:
//                Intent catIntent1 = new Intent(this, CategoryActivity.class);
//                catIntent1.putExtra("categoryId",2);
//                this.startActivity(catIntent1);
                break;
            case R.id.technology_nav_menu:
//                Intent catIntent2= new Intent(this,CategoryActivity.class);
//                catIntent2.putExtra("categoryId",3);
//                this.startActivity(catIntent2);
                break;
            case R.id.lifestyle_nav_menu:
//                Intent catIntent3= new Intent(this,CategoryActivity.class);
//                catIntent3.putExtra("categoryId",4);
//                this.startActivity(catIntent3);
                break;
            case R.id.food_nav_menu:
//                Intent catIntent4= new Intent(this,CategoryActivity.class);
//                catIntent4.putExtra("categoryId",5);
//                this.startActivity(catIntent4);
                break;
            case R.id.movies_nav_menu:
//                Intent catIntent5= new Intent(this,CategoryActivity.class);
//                catIntent5.putExtra("categoryId",1);
//                this.startActivity(catIntent5);
                break;
            case R.id.questions_nav_menu:
                sharedPreferences=getSharedPreferences("LoginData",MODE_PRIVATE);
                String loginCheckQues=sharedPreferences.getString("LoginCheck","false");

                Intent quesIntent= new Intent(MainActivity.this,MyQuestions.class);
                startActivity(quesIntent);
                break;
            case R.id.answers_nav_menu:
                sharedPreferences=getSharedPreferences("LoginData",MODE_PRIVATE);
                String loginCheckAns=sharedPreferences.getString("LoginCheck","false");

                Intent ansIntent=new Intent(MainActivity.this,MyAnswers.class);
                startActivity(ansIntent);
                break;
            case R.id.logout:
                sharedPreferences=getSharedPreferences("LoginData",MODE_PRIVATE);
                String loginCheckLogout=sharedPreferences.getString("LoginCheck","false");

//                if(logincheckLogout.equals("true")) {
//                    SharedPreferences preferences = getSharedPreferences("LoginData", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.clear();
//                    editor.commit();
//                    Intent logoutIntent = new Intent(MainActivity.this, Login.class);
//                    startActivity(logoutIntent);
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"LoginFirst",Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.details_nav_menu:
                sharedPreferences=getSharedPreferences("LoginData",MODE_PRIVATE);
                String loginCheck=sharedPreferences.getString("LoginCheck","false");

                Intent intent=new Intent(this,MyProfile.class);
                startActivity(intent);

//                if(logincheck.equals("true")) {
//                    Intent userDetails = new Intent(MainActivity.this, UserDetails.class);
//                    startActivity(userDetails);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"LoginFirst",Toast.LENGTH_SHORT).show();
//                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

}
