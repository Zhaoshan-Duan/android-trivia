/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log.i
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false)

        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        // get the arguments from the argument bundle

//        Toast.makeText(context,
//            "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}",
//            Toast.LENGTH_SHORT).show()

        // show the winner menu
        setHasOptionsMenu(true)
        return binding.root
    }

    // inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)){
            // hide the share menu item if doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    // create share intent
    private fun getShareIntent(): Intent {
        var args = GameWonFragmentArgs.fromBundle(requireArguments())

        // Using ShareCompat API
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
            .setType("text/plain")
            .intent

        // altenrative way to share
//        val shareIntent =
//            Intent(Intent.ACTION_SEND) // tells Android that we want activities that are registered with an intent filter to handle send action
//        shareIntent.setType("text/plain"). // set the type of data we are sharing to text
//        putExtra(Intent.EXTRA_TEXT, // key-value data structure
//            getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
//        return shareIntent
    }

    // sharing
    private fun shareSuccess() {
        // start the activity with new intent
        // will pop up a chooser since many activities have intent filters that can handle send text intent
        startActivity(getShareIntent())
    }

    // connect to the menu action
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // navigate to another app using custom intent
        when (item.itemId) {
            // when the id matches in the menu xml
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
