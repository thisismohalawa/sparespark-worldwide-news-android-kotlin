package sparespark.worldwide.news.ui

interface Communicator {

    fun showProgressCircular()
    fun hideProgressCircular()
    fun updateToolBarBackground(colorRes: Int)
    fun updateToolBarTitle(stringRes: Int)
    fun restartActivity()
}