package com.avgator.todamac.Customer;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avgator.todamac.CustomerMapActivity;
import com.avgator.todamac.R;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity implements DestinationAdapter.DestinationClickListener {

    RecyclerView rvDestinations;
    DestinationAdapter destinationAdapter;
    List<DestinationModel> destinationModelList = new ArrayList<>();
    Location riderLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvDestinations = findViewById(R.id.rvDestinations);
        setData();
        prepareRecyclerView();

        riderLocation = getIntent().getParcelableExtra("riderLocation");

        //Toolbar myToolbar = findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

    }

    public void setData() {
        destinationModelList.add(new DestinationModel("Provicial Jail, Tagbobolo", "0001"));
        destinationModelList.add(new DestinationModel("Kawayan   ", "0002"));//6.959241, 126.224260
        destinationModelList.add(new DestinationModel("Campo 1   ", "0003"));//6.914489, 126.271002
        destinationModelList.add(new DestinationModel("Anislag   ", "0004"));//7.010561, 126.236604Not
        destinationModelList.add(new DestinationModel("Binaguihan", "0005"));//7.010561, 126.236604
        destinationModelList.add(new DestinationModel("Licop Sainz", "0006"));//6.953061, 126.216098
        destinationModelList.add(new DestinationModel("Tawas", "0007"));//6.953061, 126.216098Not
        destinationModelList.add(new DestinationModel("Taguibo Proper", "0008"));//7.034109, 126.208478
        destinationModelList.add(new DestinationModel("Pulang Lupa", "0009"));//7.252362, 126.155271
        destinationModelList.add(new DestinationModel("Hagdan", "0010"));//6.959014, 126.224724
        destinationModelList.add(new DestinationModel("Waywayan", "0012"));//6.966894, 126.222405Not
        destinationModelList.add(new DestinationModel("Taguikan", "0013"));//6.952140, 126.217298
        destinationModelList.add(new DestinationModel("Mainas Kulian Proper", "0014"));//6.969772, 126.163410
        destinationModelList.add(new DestinationModel("Crossing Libudon", "0015"));//6.942750, 126.134325
        destinationModelList.add(new DestinationModel("Bigue 1", "0016"));//6.959273, 126.224261
        destinationModelList.add(new DestinationModel("Bigue 2", "0017"));//6.961583, 126.221738Not
        destinationModelList.add(new DestinationModel("Mamacao", "0018"));//6.966813, 126.222374Not
        destinationModelList.add(new DestinationModel("Libudon Proper", "0019"));//6.942328, 126.133762
        destinationModelList.add(new DestinationModel("Danao Proper", "0020"));//6.921318, 126.128217
        destinationModelList.add(new DestinationModel("Sanghay Proper", "0021"));//6.973936, 126.137271
        destinationModelList.add(new DestinationModel("Dawan Proper", "0022"));//6.894215, 126.149088
        destinationModelList.add(new DestinationModel("San Pedro", "0023"));//6.922982, 126.147062
        destinationModelList.add(new DestinationModel("Tagamot", "0024"));//6.891405, 126.137185
        destinationModelList.add(new DestinationModel("Lapasan", "0025"));//6.891405, 126.137185Not
        destinationModelList.add(new DestinationModel("Subay 1", "0026"));//6.891405, 126.137185Not
        destinationModelList.add(new DestinationModel("Subay 2", "0027"));//6.891405, 126.137185Not
        destinationModelList.add(new DestinationModel("Mamali Proper", "0028"));//6.860186, 126.168018
        destinationModelList.add(new DestinationModel("Tagdodo", "00029"));//6.869199, 126.175187
        destinationModelList.add(new DestinationModel("Silad", "0030"));//Not
        destinationModelList.add(new DestinationModel("Banlutan", "0031"));//Not
        destinationModelList.add(new DestinationModel("Macambol Proper", "0032"));//6.833631, 126.195890
        destinationModelList.add(new DestinationModel("Casinihan", "0033"));//Not
        destinationModelList.add(new DestinationModel("Wagon", "0034"));//6.816395, 126.205194
        destinationModelList.add(new DestinationModel("Magum", "0035"));//6.818422, 126.207070
        destinationModelList.add(new DestinationModel("Lanca", "0036"));//6.339388, 126.196077
        destinationModelList.add(new DestinationModel("Luban", "0037"));//6.431419, 126.219445
        destinationModelList.add(new DestinationModel("Cabuaya", "0038"));//6.519202, 126.215741
    }

    public void prepareRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDestinations.setLayoutManager(linearLayoutManager);
        preAdapter();
    }

    public void preAdapter() {
        destinationAdapter = new DestinationAdapter(destinationModelList, this, this::selectedDestinations);
        rvDestinations.setAdapter(destinationAdapter);
    }

    @Override
    public void selectedDestinations(DestinationModel destinationModel) {

        String desName;
        double lat, lng;
        Intent intent;

        switch (destinationModel.getDesCode()) {


            case "001":
                lat = 6.953023;
                lng = 126.216110;
                desName = "Provicial Jail, Tagbobolo";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "002":
                lat = 6.959241;
                lng = 126.224260;
                desName = "Kawayan";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "003":
                lat = 6.914489;
                lng = 126.271002;
                desName = "Campo 1";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "004":
                lat = 0.0;
                lng = 0.0;
                desName = "Anislag";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "005":
                lat = 7.010561;
                lng = 126.236604;
                desName = "Binaguihan";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "006":
                lat = 6.953061;
                lng = 126.216098;
                desName = "Licop Sainz";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "007":
                lat = 0.0;
                lng = 0.0;
                desName = "Tawas";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "008":
                lat = 7.034109;
                lng = 126.208478;
                desName = "Taguibo Proper";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0009":
                lat = 7.252362;
                lng = 126.155271;
                desName = "Pulang Lupa";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0010":
                lat = 6.959014;
                lng = 126.224724;
                desName = "Hagdan";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0012":
                lat = 0.0;
                lng = 0.0;
                desName = "Waywayan";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0013":
                lat = 6.952140;
                lng = 126.217298;
                desName = "Taguikan";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0014":
                lat = 6.969772;
                lng = 126.163410;
                desName = "Mainas Kulian Proper";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0015":
                lat = 6.942750;
                lng = 126.134325;
                desName = "Crossing Libudon";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0016":
                lat = 0.0;
                lng = 0.0;
                desName = "Bigue 1";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0017":
                lat = 0.0;
                lng = 0.0;
                desName = "Bigue 2";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

            case "0018":
                lat = 0.0;
                lng = 0.0;
                desName = "Mamacao";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0019":
                lat = 6.942328;
                lng = 126.133762;
                desName = "Libudon Proper";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0020":
                lat = 6.921318;
                lng = 126.128217;
                desName = "Danao Proper";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0021":
                lat = 6.973936;
                lng = 126.137271;
                desName = "Sanghay Proper";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0022":
                lat = 6.894215;
                lng = 126.149088;
                desName = "Dawan Proper";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0023":
                lat = 6.922982;
                lng = 126.147062;
                desName = "San Pedro";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0024":
                lat = 6.891405;
                lng = 126.137185;
                desName = "Tagamot";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0025":
                lat = 0.0;
                lng = 0.0;
                desName = "Lapasan";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0026":
                lat = 0.0;
                lng = 0.0;
                desName = "Subay 1";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0027":
                lat = 0.0;
                lng = 0.0;
                desName = "Subay 2";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0028":
                lat = 6.860186;
                lng = 126.168018;
                desName = "Mamali Proper";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "00029":
                lat = 6.869199;
                lng = 126.175187;
                desName = "Tagdodo";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0030":
                lat = 0.0;
                lng = 0.0;
                desName = "Silad";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0031":
                lat = 0.0;
                lng = 0.0;
                desName = "Banlutan";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0032":
                lat = 6.833631;
                lng = 126.195890;
                desName = "Macambol Proper";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0033":
                lat = 0.0;
                lng = 0.0;
                desName = "Casinihan";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0034":
                lat = 6.816395;
                lng = 126.205194;
                desName = "Wagon";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0035":
                lat = 6.818422;
                lng = 126.207070;
                desName = "Magum";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0036":
                lat = 6.339388;
                lng = 126.196077;
                desName = "Lanca";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0037":
                lat = 6.431419;
                lng = 126.219445;
                desName = "Luban";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;

                case "0038":
                lat = 6.519202;
                lng = 126.215741;
                desName = "Cabuaya";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
                break;


            default:

                lat = 0.0;
                lng = 0.0;
                desName = "Destination ---";

                intent = new Intent(getApplicationContext(), CustomerMapActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("destinationName", desName);
                intent.putExtra("riderLocation", riderLocation);
                startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchView) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchStr = newText;
                destinationAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            CustomerMapActivity customerMapActivity = new CustomerMapActivity();
        }
    }


}