import React from 'react';
import { Container, Grid, Box, Typography, Divider, FormControl, OutlinedInput } from '@mui/material';
import { EditorState } from 'draft-js';
import { Editor } from 'react-draft-wysiwyg';

class Write extends React.Component {
	constructor(props) {
		super(props);
		this.state = { editorState: EditorState.createEmpty() };
	}

	onEditorStateChange = (editorState) => {
		this.setState({ editorState });
	}

	render() {
		return(
			<Container maxWidth={false}>
				<Grid container alignContent="center" style={{ minHeight: "100vh" }}>
					<Box textAlign="center" mx="auto">
						<Typography align="left" gutterBottom  variant="h4">글 작성하기</Typography>
						<Divider />
						<Box component="form" width="512dp">
							<FormControl margin="normal" sx={{ width: '100ch' }}>
								<OutlinedInput placeholder="제목" />
							</FormControl>
							<FormControl margin="normal" sx={{ width: '100ch' }}>
								<Editor editorState={this.state.editorState} onEditorStateChange={this.onEditorStateChange} placeholder="내용을 입력하세요." />
							</FormControl>
						</Box>
					</Box>
				</Grid>
			</Container>
		)
	}
}

export default Write;