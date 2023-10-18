package ignacio.campillos.androidstudio_inmueblescorr_ej03;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.WindowCompat;


import ignacio.campillos.androidstudio_inmueblescorr_ej03.Modelos.EditarInmuebleActivity;
import ignacio.campillos.androidstudio_inmueblescorr_ej03.Modelos.Inmueble;
import ignacio.campillos.androidstudio_inmueblescorr_ej03.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<Inmueble> listaInmuebles;

    private ActivityResultLauncher<Intent> addInmuebleLauncher;
    private ActivityResultLauncher<Intent> editInmuebleLauncher;
    private Inmueble inmuebleOrigen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaInmuebles = new ArrayList<>();

        inicializarLauncher();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInmuebleLauncher.launch(new Intent(MainActivity.this,CrearInmuebleActivity.class));
            }
        });
    }

    private void inicializarLauncher() {
        addInmuebleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //Que hago a la vuelta de Crear Inmueble
                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData()!=null && result.getData().getExtras() != null){
                                Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable("INMUEBLE");
                                listaInmuebles.add(inmueble);
                                Toast.makeText(MainActivity.this, inmueble.toString(), Toast.LENGTH_SHORT).show();
                                mostrarInmuebles();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "CANCELADO", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        editInmuebleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //Que hago a la vuelta de Editar Inmueble
                        if (result.getResultCode() == RESULT_OK){
                            if(result.getData() != null && result.getData().getExtras() != null){ // COMPRUEBO QUE HAY INTENT Y LUEGO QUE TENGA DATOS, PARA QUE NO DE ERROR SE COMPRUEBA AMBOS
                                //MODIFICO INMUEBLE
                                Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable("INMUEBLE");
                                int posicion = result.getData().getExtras().getInt("POSICION");
                                listaInmuebles.set(posicion, inmueble);
                                mostrarInmuebles();
                            }else{
                                //ELIMINO INMUEBLE
                                listaInmuebles.remove(inmuebleOrigen);
                                mostrarInmuebles();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "ACCION CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void mostrarInmuebles() {
        binding.contentMain.contenedor.removeAllViews();

        for (int i = 0; i < listaInmuebles.size(); i++) {
            Inmueble inmueble = listaInmuebles.get(i);

            View inmuebleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_model_view,null); //Leer(mapear) para esta actividad, el xml para cargar sus componentes

            TextView textViewDireccion = inmuebleView.findViewById(R.id.textViewDireccionInmuebleModelo);
            TextView textViewNumero = inmuebleView.findViewById(R.id.textViewNumeroInmuebleModel);
            TextView textViewCiudad = inmuebleView.findViewById(R.id.textViewCiudadInmuebleModel);
            RatingBar ratingBarValoracion = inmuebleView.findViewById(R.id.ratingBarValoracionInmuebleCiudad);

            textViewDireccion.setText(inmueble.getDireccion());
            textViewNumero.setText(String.valueOf(inmueble.getNumero()));
            textViewCiudad.setText(inmueble.getCiudad());
            ratingBarValoracion.setRating(inmueble.getValoracion());
            int posicion = i;

            inmuebleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, EditarInmuebleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("INMUEBLE",inmueble);
                    bundle.putInt("POSICION",posicion);
                    intent.putExtras(bundle);
                    inmuebleOrigen = inmueble;
                    editInmuebleLauncher.launch(intent);
                }
            });

            binding.contentMain.contenedor.addView(inmuebleView);


        }
    }
}