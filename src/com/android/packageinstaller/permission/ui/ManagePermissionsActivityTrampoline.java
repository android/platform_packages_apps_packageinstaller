/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.packageinstaller.permission.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.packageinstaller.permission.service.PermissionSearchIndexablesProvider;

/**
 * Trampoline activity for {@link ManagePermissionsActivity}.
 */
public class ManagePermissionsActivityTrampoline extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (!PermissionSearchIndexablesProvider.isIntentValid(intent, this)) {
            finish();
            return;
        }

        String action = intent.getAction();
        if (action == null) {
            finish();
            return;
        }

        Intent newIntent = new Intent(this, ManagePermissionsActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        switch (action) {
            case PermissionSearchIndexablesProvider.ACTION_REVIEW_PERMISSION_USAGE:
                newIntent.setAction(Intent.ACTION_REVIEW_PERMISSION_USAGE);
                break;
            case PermissionSearchIndexablesProvider.ACTION_MANAGE_PERMISSION_APPS:
                newIntent
                        .setAction(Intent.ACTION_MANAGE_PERMISSION_APPS)
                        .putExtra(Intent.EXTRA_PERMISSION_NAME,
                                PermissionSearchIndexablesProvider.getOriginalKey(intent));
                break;
            default:
                finish();
                return;
        }

        startActivity(newIntent);
        finish();
    }
}
