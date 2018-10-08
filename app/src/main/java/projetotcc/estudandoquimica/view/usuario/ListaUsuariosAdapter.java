package projetotcc.estudandoquimica.view.usuario;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.UsuarioItemBinding;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.viewmodel.ListaEstudanteViewModel;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.BindingHolder>
        implements Filterable{

    private List<Usuario> usuarios;
    private List<Usuario> filtroUsuarios;
    private DiffUtil.DiffResult result;
    private Opcao opcao;
    private Context context;
    private static ClickAddListener clickAddListener;
    private boolean exibirBotaoAdd = true;

    public enum Opcao{

        ADD,
        REMOVER,
        NENHUM
    }

    public interface ClickAddListener{

        void click(Usuario usuario, int position);
    }

    public void add(Usuario usuario, int posicao){

        usuarios.add(posicao,usuario);
        notifyItemInserted(getItemCount());
        notifyItemRangeChanged(0, usuarios.size());
    }

    public ListaUsuariosAdapter(List<Usuario> usuarios, Opcao opcao, ClickAddListener clickAddListener, Context context) {

        this.opcao = opcao;
        this.context = context;
        //this.clickAddListener = clickAddListener;
        this.usuarios = usuarios;


    }

    public void setExibirBotaoAdd(boolean exibirBotaoAdd){

        this.exibirBotaoAdd = exibirBotaoAdd;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final UsuarioItemBinding  binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.usuario_item, parent, false);


        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {

        UsuarioItemBinding binding = holder.binding;

        ListaEstudanteViewModel model = new ListaEstudanteViewModel();
        model.setViewModel(filtroUsuarios.get(position));
        model.visibilidadeBotaoAdd.set(exibirBotaoAdd ? View.VISIBLE : View.GONE);

        binding.setUsuario(model);
    }

    @Override
    public int getItemCount() {
        return filtroUsuarios != null ? filtroUsuarios.size() : 0;
    }

    public void setUsuarios(List<Usuario> usuarios){


        if(this.usuarios == null){

            //this.usuarios = usuarios;
            this.usuarios = usuarios;
        }
        if(this.filtroUsuarios == null){

            //this.usuarios = usuarios;
            this.filtroUsuarios = usuarios;
        }

            result = DiffUtil.calculateDiff(
                    new DiffUtilUsuario(this.filtroUsuarios, usuarios), false);

            this.filtroUsuarios = usuarios;
            //notifyItemRangeChanged(0, getItemCount());
            result.dispatchUpdatesTo(this);

        result = DiffUtil.calculateDiff(
                new DiffUtilUsuario(this.usuarios, usuarios), false);

        this.usuarios = usuarios;
        result.dispatchUpdatesTo(this);


    }

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public static void circularReveal(View v){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            int finalRadius = Math.max(v.getWidth(), v.getHeight()) / 2;
//            TransitionManager.beginDelayedTransition((ViewGroup) v);
//            Animator anim = ViewAnimationUtils.createCircularReveal(v, v.getWidth() / 2, v.getHeight() / 2, 0, finalRadius);
//            anim.start();
//        }
  //  }

    public void removerItem(int position) {

        usuarios.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, usuarios.size());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filtroUsuarios = usuarios;

                } else {
                    List<Usuario> filteredList = new ArrayList<>();
                    for (Usuario row : usuarios) {

                        if (row.getNome().toLowerCase().contains(charString.toLowerCase()) || row.getEmail().contains(constraint)) {
                            filteredList.add(row);
                        }
                    }

                    filtroUsuarios = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtroUsuarios;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //filtroUsuarios = (ArrayList<Usuario>) results.values;

//                result = DiffUtil.calculateDiff(
//                        new DiffUtilUsuario(filtroUsuarios, (ArrayList<Usuario>) results.values), false);

                filtroUsuarios = (ArrayList<Usuario>) results.values;
                //notifyItemRangeChanged(0, getItemCount());
                notifyDataSetChanged();
                //result.dispatchUpdatesTo(ListaUsuariosAdapter.this);


            }
        };
    }

    public class DiffUtilUsuario extends DiffUtil.Callback {

        private List<Usuario> antigo;
        private List<Usuario> novo;


        public DiffUtilUsuario(List<Usuario> antigo, List<Usuario> novo) {
            this.antigo = antigo;
            this.novo = novo;
        }

        @Override
        public int getOldListSize() {
            return antigo != null ? antigo.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return novo != null ? novo.size() : 0;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return novo.get(newItemPosition).getId().equals(antigo.get(oldItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

            return novo.get(newItemPosition).getId().equals(antigo.get(oldItemPosition).getId())
                    && novo.get(newItemPosition).getNome().equals(antigo.get(oldItemPosition).getNome())
                    && novo.get(newItemPosition).getEmail().equals(antigo.get(oldItemPosition).getEmail())
                    && novo.get(newItemPosition).getUrlFoto().equals(antigo.get(oldItemPosition).getUrlFoto());

        }
    }

    public void setOnItemClickListener(ClickAddListener clickListener) {
        ListaUsuariosAdapter.clickAddListener = clickListener;
    }

    public class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private UsuarioItemBinding binding;

        public BindingHolder(UsuarioItemBinding binding) {
            super(binding.cardView);

            if(opcao == Opcao.ADD) {

                //binding.btnAdd.setImageDrawable(context.getDrawable(R.drawable.ic_add));
                binding.btnAdd.setVisibility(View.VISIBLE);
            }
            else{
                binding.btnAdd.setVisibility(View.GONE);
            }

            binding.btnAdd.setOnClickListener(this);

            this.binding = binding;
        }


        @Override
        public void onClick(View v) {
            clickAddListener.click(usuarios.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
