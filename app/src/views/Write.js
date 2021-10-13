import React from 'react';
import { Container, Grid, Box, Typography, Divider, FormControl, OutlinedInput, Button } from '@mui/material';
import { EditorState, RichUtils } from "draft-js";
import RichEditor from '../components/RichEditor';

export default class Write extends React.Component {
	constructor(props) {
		super(props);
		this.state = { editorState: EditorState.createEmpty() };
	}

	onClickUndo = () => {
		this.onEditorStateChange(EditorState.undo(this.state.editorState));
	}

	onClickRedo = () => {
		this.onEditorStateChange(EditorState.redo(this.state.editorState));
	}

	onToggleInlineStyle = (style) => (event) => {
		event.preventDefault();
		this.onEditorStateChange(RichUtils.toggleInlineStyle(this.state.editorState, style));
	}

	onEditorChange = (editorState) => {
		this.setState({ editorState });
	}

	render() {
		return(
			<Container maxWidth={false}>
				<Grid container alignContent="center" height="100vh">
				<Box sx={{ height: '64px' }} />
					<Box textAlign="center" mx="auto"  width="1920px" maxWidth="1920px">
						<Typography align="left" gutterBottom  variant="h4">게시물 작성</Typography>
						<Divider />
						<Box component="form">
							<FormControl margin="normal" sx={{ width: '100%' }}>
								<OutlinedInput placeholder="제목" />
							</FormControl>
							<FormControl margin="normal" sx={{ width: '100%' }}>
								<RichEditor editorState={this.state.editorState} onChange={this.onEditorChange.bind(this)} />
							</FormControl>
							<Button fullWidth variant="contained" sx={{ mt: 1, mb: 2 }}>작성</Button>
						</Box>
					</Box>
				</Grid>
			</Container>
		)
	}
}