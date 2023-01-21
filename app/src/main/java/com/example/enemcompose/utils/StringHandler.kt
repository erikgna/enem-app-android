package com.example.enemcompose.utils

class StringHandler {
    companion object {
        fun transformTextToWidget(desc: String): ArrayList<String> {
            val splited = desc.split("<img ")
            val widgets = ArrayList<String>()

            splited.forEach {
                if (it.contains("src")) {
                    widgets.add(it.substring(5, it.indexOf(">") - 3))
                    widgets.add(it.substring(it.indexOf(">") + 1))
                } else {
                    widgets.add(it.replace("<br />", "\n"))
                }
            }

            return widgets
        }
    }
}