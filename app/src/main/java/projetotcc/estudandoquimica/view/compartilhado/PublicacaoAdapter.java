package projetotcc.estudandoquimica.view.compartilhado;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;
import java.util.List;
import java.util.Objects;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.PublicacaoItemBinding;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;

public class PublicacaoAdapter extends RecyclerView.Adapter<PublicacaoAdapter.BindingHolder> {

    private List<Publicacao> lista_publicacao;
    private Context context;

    public PublicacaoAdapter(List<Publicacao> lista_publicacao, Context context) {
        this.lista_publicacao = lista_publicacao;
        this.context = context;
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
    public void onBindViewHolder(@NonNull PublicacaoAdapter.BindingHolder holder, int position) {
        PublicacaoItemBinding binding = holder.binding;

        PublicacaoViewModel publicacaoViewModel = new PublicacaoViewModel(lista_publicacao.get(position), context);

        onBotaoCurtirClicked(binding, publicacaoViewModel);

        binding.setPublicacao(publicacaoViewModel);
    }

    @Override
    public int getItemCount() {
        return lista_publicacao.size();
    }

    private void onBotaoCurtirClicked(final PublicacaoItemBinding binding, final PublicacaoViewModel viewModel){

        binding.viewSwitcher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewSwitcher switcher = (ViewSwitcher) v;

                if (switcher.getDisplayedChild() == 0) {

                    if(Objects.equals(Objects.requireNonNull(
                            context.getDrawable(R.drawable.ic_flask_cheio)).getConstantState(),
                            binding.imageCurtir.getDrawable().getConstantState())){
                        viewModel.setCurtida(false);
                        binding.imageCurtir.setImageResource(R.drawable.ic_flask_vazio);
                    }else {
                        binding.imageCurtir.setImageResource(R.drawable.ic_flask_cheio);
                        viewModel.setCurtida(true);

                    }
                    switcher.showNext();
                } else {

                    if(Objects.equals(Objects.requireNonNull(
                            context.getDrawable(R.drawable.ic_flask_cheio)).getConstantState(),
                            binding.imageCurtir.getDrawable().getConstantState())){

                        binding.imageCurtir.setImageResource(R.drawable.ic_flask_vazio);
                        viewModel.setCurtida(false);
                    }else {
                        binding.imageCurtir.setImageResource(R.drawable.ic_flask_cheio);
                        viewModel.setCurtida(true);
                    }
                    switcher.showPrevious();
                }
            }
        });
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private PublicacaoItemBinding binding;

        private BindingHolder(PublicacaoItemBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

    }
}
