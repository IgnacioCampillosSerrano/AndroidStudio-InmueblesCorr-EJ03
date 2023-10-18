package ignacio.campillos.androidstudio_inmueblescorr_ej03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ignacio.campillos.androidstudio_inmueblescorr_ej03.Modelos.Inmueble;
import ignacio.campillos.androidstudio_inmueblescorr_ej03.databinding.ActivityCrearInmuebleBinding;

public class CrearInmuebleActivity extends AppCompatActivity {

    private ActivityCrearInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCrearInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCancelarCrearInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.buttonCrearCrearInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmueble inmueble = crearInmueble();

                if(inmueble != null){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("INMUEBLE", inmueble);
                    intent.putExtras(bundle);

                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(CrearInmuebleActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Inmueble crearInmueble() {

        if (binding.editTextDireccionCrearInmueble.getText().toString().isEmpty()
        || binding.editTextNumCrearInmueble.getText().toString().isEmpty()
        || binding.editTextCiudadCrearInmueble.getText().toString().isEmpty()
        || binding.editTextProvinciaCrearInmueble.getText().toString().isEmpty()
        || binding.editTextCPCrearInmueble.getText().toString().isEmpty()
        || binding.ratingBarValoracionCrearInmueble.getRating() < 0.5){
            return null;
        }


        return new Inmueble(
                binding.editTextDireccionCrearInmueble.getText().toString(),
                Integer.parseInt(binding.editTextNumCrearInmueble.getText().toString()),
                binding.editTextCiudadCrearInmueble.getText().toString(),
                binding.editTextProvinciaCrearInmueble.getText().toString(),
                binding.editTextCPCrearInmueble.getText().toString(),
                binding.ratingBarValoracionCrearInmueble.getRating()
        );
    }
}