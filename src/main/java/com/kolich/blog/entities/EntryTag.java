/**
 * Copyright (c) 2015 Mark S. Kolich
 * http://mark.koli.ch
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.kolich.blog.entities;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URLEncoder;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

public final class EntryTag {

    private static final String UTF_8_CHARSET = Charsets.UTF_8.name();

    private final String tag_;

    public EntryTag(@Nonnull final String tag) {
        tag_ = checkNotNull(tag, "Entry tag cannot be null.");
    }

    /**
     * Returns the raw tag, as ingested from the content.  This version is suitable for display only,
     * and is not safe to be used on a URL.  This method is typically invoked from within a Freemarker
     * template at render time.
     */
    public String getDisplayText() {
        return escapeTag(tag_);
    }

    /**
     * Returns a tag that has been URL encoded, and is suitable to be appended onto a URL.
     */
    @Nullable
    public String getUrlEncodedText() {
        return encodeTag(tag_);
    }

    /**
     * Helper method to URL encode the incoming tag.
     */
    public static final String encodeTag(final String tag) {
        try {
            return URLEncoder.encode(tag, UTF_8_CHARSET);
        } catch (Exception e) {
            // Ugh, is this really even possible on modern stacks?
            throw new RuntimeException("Failed miserably to UTF-8 URL encode entry tag: " + tag, e);
        }
    }

    /**
     * Helper method that HTML escapes the incoming tag.  Tags with HTML unsafe characters in them
     * are escaped to their HTML entity equivalents.  See the Javadoc for {@link StringEscapeUtils#escapeHtml4(String)}
     * for more details.
     */
    public static final String escapeTag(final String tag) {
        return escapeHtml4(tag);
    }

    /**
     * Helper convenience method to URL encode and HTML escape the incoming tag in a single call.
     */
    public static final String encodeAndEscapeTag(final String tag) {
        return escapeTag(encodeTag(tag));
    }

}
