package riegatucultivo.hackaton.org.riegatucultivo.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import riegatucultivo.hackaton.org.riegatucultivo.Modelo.Recomendacion;
import riegatucultivo.hackaton.org.riegatucultivo.R;

/**
 * Created by Sebas on 03/10/2015.
 */
public class RVAdapterRecomendaciones extends RecyclerView.Adapter<RVAdapterRecomendaciones.RecomendacionViewHolder> {



    private List<Recomendacion> recomendaciones;

    public RVAdapterRecomendaciones(List<Recomendacion> recomendacions)
    {
        this.recomendaciones = recomendacions;
    }


    /*
    Añade una lista completa de servicios
     */
    public void addAll(ArrayList<Recomendacion> lista){
        recomendaciones.addAll(lista);
        //    Collections.sort(servicios);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear(){
        recomendaciones.clear();
        notifyDataSetChanged();
    }


    @Override
    public RecomendacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recomendacion, parent, false);
        RecomendacionViewHolder pvh = new RecomendacionViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RecomendacionViewHolder recomendacionViewHolder, int position) {
        recomendacionViewHolder.nombre_sector.setText(recomendaciones.get(position).getRecomendacion());
        recomendacionViewHolder.recomendacion.setText("LA RECOMENDACIÓN");

/*
        recomendacionViewHolder.imagen_tipoCultivo.setImageResource();
        recomendacionViewHolder.imagen_tipoRecomendacion.setImageResource(recomendaciones.get(position).getImagenCategoria());
*/

    }



    @Override
    public int getItemCount() {
        return recomendaciones == null ? 0 : recomendaciones.size();
    }




    public class RecomendacionViewHolder extends RecyclerView.ViewHolder{
        private CardView cv_recomendacion;
        private TextView nombre_sector, recomendacion;
        private ImageView imagen_tipoCultivo, imagen_tipoRecomendacion;

        public RecomendacionViewHolder(View itemView) {
            super(itemView);
            nombre_sector = (TextView) itemView.findViewById(R.id.it_nombre_sector);
            recomendacion = (TextView) itemView.findViewById(R.id.it_recomendacion);
            imagen_tipoCultivo = (ImageView) itemView.findViewById(R.id.it_tipo_cultivo);
            imagen_tipoRecomendacion = (ImageView) itemView.findViewById(R.id.it_alerta_icono);
        }

    }
}
