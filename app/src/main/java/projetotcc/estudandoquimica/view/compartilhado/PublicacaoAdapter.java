package projetotcc.estudandoquimica.view.compartilhado;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.List;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.componentesPersonalizados.StatefulRecyclerView;
import projetotcc.estudandoquimica.databinding.PublicacaoItemBinding;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;

public class PublicacaoAdapter extends StatefulRecyclerView.Adapter<PublicacaoAdapter.BindingHolder> {

    private List<Publicacao> lista_publicacao;
    private Context context;
    private BotoesClickListener botoesClickListener;

    public PublicacaoAdapter(List<Publicacao> lista_publicacao,
                             Context context, BotoesClickListener botoesClickListener) {
        this.context = context;
        //this.lista_publicacao = lista_publicacao;
        setHasStableIds(true);
        this.botoesClickListener = botoesClickListener;
    }


    public void setPublicacao(List<Publicacao> lista_publicacao) {

        if(this.lista_publicacao == null) {
            this.lista_publicacao = lista_publicacao;
            notifyDataSetChanged();
        }
    }

    public void addPublicacao(Publicacao publicacao, int posicao){

        lista_publicacao.add(publicacao);
        notifyItemInserted(getItemCount());
        notifyItemRangeChanged(0, lista_publicacao.size());
    }

    public void remover(int posicao){

        lista_publicacao.remove(posicao);

        notifyItemRemoved(posicao);
        notifyItemRangeRemoved(posicao, getItemCount());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public PublicacaoAdapter.BindingHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        final PublicacaoItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.publicacao_item, parent, false);

        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
        }
        else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if(key.equals("curtiu")){

                    Toast.makeText(holder.itemView.getContext(), "Contact "+position+" : Name Changed", Toast.LENGTH_SHORT).show();

                    PublicacaoViewModel model = new PublicacaoViewModel();

                    model.setPublicacao(lista_publicacao.get(position), context);
                    holder.binding.setPublicacao(model);

                }

            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull PublicacaoAdapter.BindingHolder holder, int position) {
        PublicacaoItemBinding binding = holder.binding;

        PublicacaoViewModel publicacaoViewModel = new PublicacaoViewModel();

        publicacaoViewModel.setPublicacao(lista_publicacao.get(position),context);

        onBotaoCurtirClicked(binding, publicacaoViewModel, position);
//        binding.viewSwitcher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                botoesClickListener.curtir(binding, lista_publicacao.get(position), publicacaoViewModel);
//            }
//        });

        onBotaoComentarClicked(binding, position);

        binding.setPublicacao(publicacaoViewModel);
    }

    @Override
    public int getItemCount() {
        return lista_publicacao != null ? lista_publicacao.size() : 0;
    }

    private void onBotaoCurtirClicked(final PublicacaoItemBinding binding, final PublicacaoViewModel viewModel, int position){

        binding.viewSwitcher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewSwitcher switcher = (ViewSwitcher) v;
                //switcher.setDisplayedChild(viewModel.curtiu.get());
                //viewModel.addCurtida();
                if (switcher.getDisplayedChild() == 0) {

                   // switcher.findViewById(R.id.)
                    if(viewModel.iconeCurtida.get().getConstantState().
                            equals(context.getDrawable(R.drawable.ic_flask_vazio).getConstantState())){

                        //viewModel.curtiu.set(0);
                        //binding.imageCurtir.setImageResource(R.drawable.ic_flask_vazio);
                        //viewModel.curtida();
                        viewModel.setI(true);
                        //viewModel.iconeCurtida.set(context.getDrawable(R.drawable.ic_flask_vazio));

                    }else {

                        //viewModel.iconeCurtida.set(context.getDrawable(R.drawable.ic_flask_cheio));
                       // viewModel.curtiu.set(1);
                       // viewModel.curtida();
                        viewModel.setI(false);
                    }
                    viewModel.addCurtida();
                    switcher.showNext();


                } else {

                    if(viewModel.iconeCurtida.get().getConstantState().
                            equals(context.getDrawable(R.drawable.ic_flask_vazio).getConstantState())){

                        //viewModel.curtiu.set(0);
                        //binding.imageCurtir.setImageResource(R.drawable.ic_flask_vazio);
                        //viewModel.curtida();
                        viewModel.setI(true);
                        //viewModel.iconeCurtida.set(context.getDrawable(R.drawable.ic_flask_vazio));

                    }else {

                        //viewModel.iconeCurtida.set(context.getDrawable(R.drawable.ic_flask_cheio));
                        // viewModel.curtiu.set(1);
                        // viewModel.curtida();
                        viewModel.setI(false);
                    }

                    viewModel.addCurtida();
                    switcher.showPrevious();

                }


            }
        });
    }

    private void onBotaoComentarClicked(final PublicacaoItemBinding binding, int pos){

        binding.viewSwitcherComentar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewSwitcher switcher = (ViewSwitcher) v;

                //if (switcher.getDisplayedChild() == 0) {

                    switcher.showNext();
                botoesClickListener.comentar(lista_publicacao.get(pos));
//
//                } else {
//
//                    switcher.showPrevious();
//                }
            }
        });
    }

    public void updatePublicacao(List<Publicacao> newPublicacoes){

        if(this.lista_publicacao == null){

            this.lista_publicacao = newPublicacoes;
        }

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return lista_publicacao != null ? lista_publicacao.size() : 0;
            }

            @Override
            public int getNewListSize() {
                return newPublicacoes != null ? newPublicacoes.size() : 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return lista_publicacao.get(oldItemPosition).getId().
                        equals(newPublicacoes.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                notifyItemChanged(newItemPosition);

                Publicacao antiga = lista_publicacao.get(oldItemPosition);
                Publicacao nova = newPublicacoes.get(newItemPosition);

                return   antiga.getCurtiu() == nova.getCurtiu()
                        //&& antiga.getAdmin().equals(nova.getAdmin())
                        //&& antiga.getDataPublicacao().equals(nova.getDataPublicacao())
                        //&& antiga.getId().equals(nova.getId())
                        //&& antiga.getImagemUrl().equals(nova.getImagemUrl())
                        && antiga.getNumComentarios() == nova.getNumComentarios()
                        && antiga.getNumCurtidas() == nova.getNumCurtidas();
                        //&& antiga.getTextoPublicacao().equals(nova.getTextoPublicacao())
                        //&& antiga.getTitulo().equals(nova.getTitulo());
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {


               notifyItemChanged(newItemPosition);

                Publicacao antiga = lista_publicacao.get(oldItemPosition);
                Publicacao nova = newPublicacoes.get(newItemPosition);

                Bundle bundle = new Bundle();

                if(antiga.getCurtiu() != nova.getCurtiu()){
                    bundle.putBoolean("curtiu", nova.getCurtiu());
                }

                if(antiga.getNumComentarios() != nova.getNumComentarios()){

                    bundle.putInt("numComentarios", nova.getNumComentarios());
                }

                if(antiga.getNumCurtidas() != nova.getNumCurtidas()){

                    bundle.putInt("numCurtidas", nova.getNumCurtidas());
                }

                if (bundle.size()==0){
                    return null;
                }
                return bundle;

            }
        });


        lista_publicacao.clear();
        lista_publicacao = newPublicacoes;

        diffResult.dispatchUpdatesTo(this);

        //notifyDataSetChanged();

    }

    public class BindingHolder extends RecyclerView.ViewHolder {

        private PublicacaoItemBinding binding;

        private BindingHolder(PublicacaoItemBinding binding) {
            super(binding.cardView);
            this.binding = binding;

            //onBotaoCurtirClicked(binding, binding.getPublicacao(), getAdapterPosition());

            binding.viewSwitcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    botoesClickListener.curtir(binding, lista_publicacao.get(getAdapterPosition()), binding.getPublicacao());
                    //botoesClickListener.comentar(lista_publicacao.get(getAdapterPosition()));
                }
            });

            binding.viewSwitcherComentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    botoesClickListener.comentar(lista_publicacao.get(getAdapterPosition()));
                }
            });

            binding.qtdComentarios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    botoesClickListener.btnComentariosClick(lista_publicacao.get(getAdapterPosition()));
                }
            });

            binding.conteudo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    botoesClickListener.onClickPublicacao(lista_publicacao.get(getAdapterPosition()));
                }
            });

            binding.conf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    botoesClickListener.onClickConfPublicacao(binding.conf,
                            lista_publicacao.get(getAdapterPosition()), getAdapterPosition());

                }
            });

            binding.qtdCurtidas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    botoesClickListener.clickCurtidas(lista_publicacao.get(getAdapterPosition()).getId());
                }
            });
        }


    }

    public interface BotoesClickListener {

        void curtir(PublicacaoItemBinding binding, Publicacao publicacao, PublicacaoViewModel publicacaoViewModel);
        void clickCurtidas(String idPublicacao);
        void comentar(Publicacao publicacao);
        void btnComentariosClick(Publicacao publicacao);
        void onClickPublicacao(Publicacao publicacao);
        void onClickConfPublicacao(View v, Publicacao publicacao, int posicao);
    }
}
