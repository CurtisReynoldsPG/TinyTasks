package com.example.to_do



interface OnItemClickListener
{
    fun itemClick(index: Int)
    fun itemLongClick(index: Int)
}
interface FragmentClickListener
{
    fun onLogoutButton()
    fun onCancelButton()
    fun onLoginButton()
}
