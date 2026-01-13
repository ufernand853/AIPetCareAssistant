package com.example.aipetcareassistant.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class ApiCallLogEntry(
    val method: String,
    val url: String,
    val statusCode: Int,
    val durationMs: Long,
    val timestampMillis: Long
)

object ApiCallLogStore {
    private const val MAX_ENTRIES = 200
    private val _entries = mutableStateListOf<ApiCallLogEntry>()
    val entries: SnapshotStateList<ApiCallLogEntry> = _entries

    fun add(entry: ApiCallLogEntry) {
        _entries.add(0, entry)
        if (_entries.size > MAX_ENTRIES) {
            _entries.removeAt(_entries.lastIndex)
        }
    }

    fun clear() {
        _entries.clear()
    }
}
