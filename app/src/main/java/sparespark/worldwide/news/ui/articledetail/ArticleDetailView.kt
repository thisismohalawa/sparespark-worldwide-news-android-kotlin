package sparespark.worldwide.news.ui.articledetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import sparespark.worldwide.news.data.db.dao.ArticleEntry
import sparespark.worldwide.news.databinding.ArticledetailViewBinding

class ArticleDetailView : Fragment() {

    // article args
    private val args: ArticleDetailViewArgs by navArgs()
    private val argsArticle: ArticleEntry
        get() = args.article

    // bind
    private var _binding: ArticledetailViewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ArticledetailViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.article = argsArticle

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.article = null
    }
}